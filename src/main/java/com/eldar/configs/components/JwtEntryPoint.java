package com.eldar.configs.components;

import com.eldar.api.models.responses.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author caito Vilas
 * date 08/2024
 * This class is responsible for handling authentication errors.
 */
@Component
@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {
    /**
     * Handles the authentication error.
     *
     * @param request       the request.
     * @param response      the response.
     * @param authException the authentication exception.
     * @throws IOException      if an error occurs.
     * @throws ServletException if an error occurs.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        final String MSG = "Unauthorized";
        var res = ExceptionResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .message(MSG)
                .method(request.getMethod())
                .path(request.getRequestURL().toString())
                .build();
        log.error(MSG);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String apiError = mapper.writeValueAsString(res);
        response.getWriter().write(apiError);
    }
}
