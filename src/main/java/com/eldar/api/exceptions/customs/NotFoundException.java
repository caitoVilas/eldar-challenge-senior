package com.eldar.api.exceptions.customs;

/**
 * @author  caito Vilas
 * @date 08/2024
 * Exception to handle not found resources
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
