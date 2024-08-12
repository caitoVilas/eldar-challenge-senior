package com.eldar.api.exceptions.handlers;

import com.eldar.api.exceptions.customs.BadRequestException;
import com.eldar.api.models.responses.ExceptionResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * @author caito Vilas
 * date: 08/2024
 * BadRequestExceptionHandler
 */
@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestExceptionHandler {

    /**
     * Handle BadRequestException
     *
     * @param ex      BadRequestException
     * @param request HttpServletRequest
     * @return ExceptionResponses
     */
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ExceptionResponses> badRequestHandler(BadRequestException ex,
                                                                   HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponses.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .messages(ex.getMessages())
                .method(request.getMethod())
                .path(request.getRequestURL().toString())
                .build());
    }
}
