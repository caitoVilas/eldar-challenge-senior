package com.eldar.services.contracts;

import com.eldar.api.models.responses.UserResponse;

/**
 * @author caito Vilas
 * date: 08/2024
 * Service class for admin service
 */
public interface AdminService {
    UserResponse AssingAdmin(Long userId);
    UserResponse deallocateAdmin(Long userId);
}
