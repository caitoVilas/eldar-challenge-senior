package com.eldar.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caito Vilas
 * date: 08/2024
 * ChangePasswordRequest
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class ChangePasswordRequest implements Serializable {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
