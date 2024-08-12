package com.eldar.api.models.responses;

import com.eldar.persistence.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author  caito Vilas
 * Date 08/2021
 * Description class UserResponse
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserResponse implements Serializable {
    private Long id;
    private String fullName;
    private String email;
    private String telephone;
    private String address;
    private Set<Role> roles;
}
