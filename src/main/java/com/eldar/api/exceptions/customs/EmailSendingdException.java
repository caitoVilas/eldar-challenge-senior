package com.eldar.api.exceptions.customs;

/**
 * @author caito Vilas
 * date: 08/2024
 * Custom exception for email sending
 */
public class EmailSendingdException extends RuntimeException {
    public EmailSendingdException(String message) {
        super(message);
    }
}
