package com.eldar.services.contracts;

import com.eldar.api.models.requests.ChangePasswordRequest;
import com.eldar.api.models.requests.UserRequest;
import com.eldar.api.models.requests.UserUpdateRequest;
import com.eldar.api.models.responses.UserResponse;

import java.util.List;

/**
 * @author caito Vilas
 * date: 08/2024
 * User service interface
 */
public interface UserService {
    void registerUser(UserRequest request);
    void confirmRegistration(String token);
    UserResponse getUser(Long id);
    void changePassword(ChangePasswordRequest request);
    List<UserResponse> getUsers();
    UserResponse updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);
}
