package com.eldar.api.exceptions.handlers;

import com.eldar.api.exceptions.customs.EmailSendingdException;
import com.eldar.api.models.responses.ExceptionResponse;
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
 * This class is responsible for handling exceptions of type EmailSendingdException
 * and returning a response with the error message and the status code 500
 */
@RestControllerAdvice
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailSendingExceptionHandler {

    /**
     * This method is responsible for handling exceptions of type EmailSendingdException
     * and returning a response with the error message and the status code 500
     * @param ex
     * @param request
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(EmailSendingdException.class)
    protected ResponseEntity<ExceptionResponse> emailSendingHandler(EmailSendingdException ex,
                                                                    HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body( ExceptionResponse.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .timestamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .method(request.getMethod())
                        .path(request.getRequestURL().toString())
                        .build());
    }
}
