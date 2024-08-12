package com.eldar.services.impl;

import com.eldar.api.exceptions.customs.BadRequestException;
import com.eldar.api.exceptions.customs.NotFoundException;
import com.eldar.api.models.responses.UserResponse;
import com.eldar.persistence.entities.UserEntity;
import com.eldar.persistence.repository.RoleRepository;
import com.eldar.persistence.repository.UserRepository;
import com.eldar.services.contracts.AdminService;
import com.eldar.utils.constants.UserConstants;
import com.eldar.utils.enums.RoleName;
import com.eldar.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author caito Vilas
 * date: 08/2024
 * Service class for admin service implementation
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    /**
     * Method to assign admin role to a user
     * @param userId
     * @return UserResponse
     */
    @Override
    @Transactional
    public UserResponse AssingAdmin(Long userId) {
        log.info("--> Assigning admin service");
        var user = getUser(userId);
        if(user.getRoles().stream().anyMatch(role -> role.getRole().equals(RoleName.ROLE_ADMIN))) {
            throw new BadRequestException(List.of(UserConstants.USER_ALREADY_ADMIN));
        }
        var role = roleRepository.findByRole(RoleName.ROLE_ADMIN).orElseThrow(() ->
                new NotFoundException(UserConstants.ROLE_NOT_FOUND));
        user.getRoles().add(role);
        var userUpdated = userRepository.save(user);
        return UserMapper.mapToDto(userUpdated);
    }

    /**
     * Method to deallocate admin role to a user
     * @param userId
     * @return UserResponse
     */
    @Override
    public UserResponse deallocateAdmin(Long userId) {
        log.info("--> Deallocating admin service");
        var user = getUser(userId);
        if(user.getRoles().stream().noneMatch(role -> role.getRole().equals(RoleName.ROLE_ADMIN))) {
            throw new BadRequestException(List.of(UserConstants.ROLE_NOT_ASSIGNED));
        }
        var role = roleRepository.findByRole(RoleName.ROLE_ADMIN).orElseThrow(() ->
                new NotFoundException(UserConstants.ROLE_NOT_FOUND));
        user.getRoles().remove(role);
        var userUpdated = userRepository.save(user);
        return UserMapper.mapToDto(userUpdated);
    }

    /**
     * Method to get a user by id
     * @param userId
     * @return UserEntity
     */
    private UserEntity getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(UserConstants.USER_NOT_FOUND));
    }
}
