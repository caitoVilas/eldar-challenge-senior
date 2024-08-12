package com.eldar.api.exceptions.customs;


import lombok.Getter;

import java.util.List;

/**
 * @author  caito Vilas
 * date 08/2024
 * BadRequestException class
 */
@Getter
public class BadRequestException extends RuntimeException {
    private List<String> messages;
    public BadRequestException(List<String> message) {
        super();
        this.messages = message;
    }
}
