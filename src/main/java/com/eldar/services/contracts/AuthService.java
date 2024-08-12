package com.eldar.services.contracts;

import com.eldar.api.models.requests.LoginRequest;
import com.eldar.api.models.responses.LoginResponse;

/**
 * @author caito Vilas
 * date: 08/2024
 * AuthService interface
 */
public interface AuthService {
    LoginResponse login(LoginRequest request);
}
