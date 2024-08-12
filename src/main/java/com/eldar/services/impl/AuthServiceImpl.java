package com.eldar.services.impl;

import com.eldar.api.models.requests.LoginRequest;
import com.eldar.api.models.responses.LoginResponse;
import com.eldar.configs.components.JwtProvider;
import com.eldar.services.contracts.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    /**
     * Login method
     * @param request
     * @return
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("Login service");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = jwtProvider.generateToken(authentication);
        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
