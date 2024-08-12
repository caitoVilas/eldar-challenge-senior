package com.eldar.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caito Vilas
 * date: 08/2024
 * LoginResponse class
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class LoginResponse implements Serializable {
    private String token;
}
