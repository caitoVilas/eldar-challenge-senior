package com.eldar.services.impl;

import com.eldar.api.exceptions.customs.BadRequestException;
import com.eldar.api.exceptions.customs.NotFoundException;
import com.eldar.api.models.requests.ChangePasswordRequest;
import com.eldar.api.models.requests.UserRequest;
import com.eldar.api.models.requests.UserUpdateRequest;
import com.eldar.api.models.responses.UserResponse;
import com.eldar.persistence.entities.UserEntity;
import com.eldar.persistence.entities.ValidationToken;
import com.eldar.persistence.repository.RoleRepository;
import com.eldar.persistence.repository.UserRepository;
import com.eldar.persistence.repository.ValidationTokenRepository;
import com.eldar.services.contracts.EmailService;
import com.eldar.services.contracts.UserService;
import com.eldar.services.helpers.ValidateUserHelper;
import com.eldar.utils.constants.UserConstants;
import com.eldar.utils.enums.RoleName;
import com.eldar.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author caito Vilas
 * date: 08/2024
 * Service class for user service implementation
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ValidationTokenRepository validationTokenRepository;

    /**
     * Method to register a user
     * @param request
     */
    @Override
    @Transactional
    public void registerUser(UserRequest request) {
        log.info("--> Registering user service");
        this.validateUser(request);
        var user = UserMapper.mapToEntity(request);
        var role = roleRepository.findByRole(RoleName.ROLE_USER).orElseThrow(() ->
                new NotFoundException(UserConstants.ROLE_NOT_FOUND));
        user.setRoles(Set.of(role));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var userNew = userRepository.save(user);
        this.sendNotification(userNew);
    }

    /**
     * Method to confirm registration
     * @param token
     */
    @Override
    @Transactional
    public void confirmRegistration(String token) {
        log.info("--> confirmRegistration service");
        var validationToken = validationTokenRepository.findByToken(token).orElseThrow(() ->
                new NotFoundException(UserConstants.TOKEN_NOT_FOUND));
        var user = userRepository.findByEmail(validationToken.getEmail()).orElseThrow(() ->
                new NotFoundException(UserConstants.USER_NOT_FOUND));
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        userRepository.save(user);
        validationTokenRepository.delete(validationToken);
        emailService.sendEmail(new String[]{user.getEmail()}, "Account Activated - No Reply",
                "Your account has been activated successfully");
    }

    /**
     * Method to get a user
     * @param id
     */
    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        log.info("--> getUser service");
        var user = this.getUserById(id);
        return UserMapper.mapToDto(user);
    }

    /**
     * Method to change a password
     * @param request
     */
    @Override
    @Transactional(readOnly = true)
    public void changePassword(ChangePasswordRequest request) {
        log.info("--> changePassword service");
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
            log.error(UserConstants.USER_NOT_FOUND);
            return new NotFoundException(UserConstants.USER_NOT_FOUND);
        });
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            log.error(UserConstants.USER_PASSWORD_INCORRECT);
            throw new BadRequestException(List.of(UserConstants.USER_PASSWORD_INCORRECT));
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            log.error(UserConstants.USER_PASSWORD_NO_MATCH);
            throw new BadRequestException(List.of(UserConstants.USER_PASSWORD_NO_MATCH));
        }
        if (!ValidateUserHelper.validatePassword(request.getNewPassword())) {
            log.error(UserConstants.USER_PASSWORD_INVALID);
            throw new BadRequestException(List.of(UserConstants.USER_PASSWORD_INVALID));
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    /**
     * Method to get users
     * @return List<UserResponse>
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        log.info("--> getUsers service");
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(UserMapper::mapToDto).toList();
    }

    /**
     * Method to update a user
     * @param id
     * @param request
     * @return UserResponse
     */
    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        log.info("--> updateUser service");
        List<String> errors = new ArrayList<>();
        var user = this.getUserById(id);
        if (request.getFullName() != null && !request.getFullName().isEmpty()) {
            user.setFullName(request.getFullName());
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!ValidateUserHelper.validateEmail(request.getEmail())) {
                errors.add(UserConstants.USER_EMAIL_INVALID);
            }else if (userRepository.userForEmail(id, request.getEmail()).isPresent()) {
                errors.add(UserConstants.USER_EMAIL_EXISTS);
            }
            if (!errors.isEmpty()) {
                throw new BadRequestException(errors);
            }
            user.setEmail(request.getEmail());
        }
        if (request.getTelephone()!= null && !request.getTelephone().isEmpty()) {
            user.setTelephone(request.getTelephone());
        }
        if (request.getAddress() != null && !request.getAddress().isEmpty()) {
            user.setAddress(request.getAddress());
        }
        var userUpdated = userRepository.save(user);
        return UserMapper.mapToDto(userUpdated);
    }

    /**
     * Method to delete a user
     * @param id
     */
    @Override
    public void deleteUser(Long id) {
        log.info("--> deleteUser service");
        var user = this.getUserById(id);
        userRepository.delete(user);
    }

    /**
     * Method to validate a user
     * @param request
     */
    private void validateUser(UserRequest request) {
        log.info("--> Validating user service");
        List<String> errors = new ArrayList<>();
        if (request.getFullName() == null || request.getFullName().isEmpty()) {
            errors.add(UserConstants.USER_FULL_NAME_REQUIRED);
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            errors.add(UserConstants.USER_EMAIL_REQUIRED);
        }else if (!ValidateUserHelper.validateEmail(request.getEmail())) {
            errors.add(UserConstants.USER_EMAIL_INVALID);
        }else if (userRepository.existsByEmail(request.getEmail())) {
            errors.add(UserConstants.USER_EMAIL_EXISTS);
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            errors.add(UserConstants.USER_PASSWORD_REQUIRED);
        }else if (!ValidateUserHelper.validatePassword(request.getPassword())) {
            errors.add(UserConstants.USER_PASSWORD_INVALID);
        }
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            errors.add(UserConstants.USER_PASSWORD_NO_MATCH);
        }
        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
    }

    /**
     * Method to send a notification
     * @param user
     */
    private void sendNotification(UserEntity user){
        log.info("--> sendNotification service");
        var token = ValidationToken.builder()
                .token(UUID.randomUUID().toString())
                .expirationDate(LocalDateTime.now())
                .email(user.getEmail())
                .build();
        validationTokenRepository.save(token);
        Map<String, String> data = new HashMap<>();
        data.put("name", user.getFullName());
        data.put("token", token.getToken());
        emailService.sendEmailWithTemplate(new String[]{user.getEmail()},
                "Account Activation - No Reply",
                "templates/account-activation.html",
                data);
    }

    /**
     * Method to get a user by id
     * @param id
     * @return AppUser
     */
    private UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(UserConstants.USER_NOT_FOUND));
    }
}

