package com.eldar.api.exceptions.handlers;

import com.eldar.api.exceptions.customs.NotFoundException;
import com.eldar.api.models.responses.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ExceptionResponse> notFoundHandler(NotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .method(request.getMethod())
                .path(request.getRequestURL().toString())
                .build());
    }
}
