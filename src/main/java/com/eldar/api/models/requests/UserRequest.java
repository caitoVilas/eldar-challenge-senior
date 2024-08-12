package com.eldar.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author  caito Vilas
 * Date 08/2021
 * Description class UserRequest
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserRequest implements Serializable {
    private String fullName;
    private String email;
    private String telephone;
    private String address;
    private String password;
    private String repeatPassword;
}
