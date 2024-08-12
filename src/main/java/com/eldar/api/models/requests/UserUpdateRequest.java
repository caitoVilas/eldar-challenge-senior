package com.eldar.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caito Vilas
 * Date 08/2021
 * Description class UserUpdateRequest
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserUpdateRequest implements Serializable {
    private String fullName;
    private String email;
    private String telephone;
    private String address;
}
