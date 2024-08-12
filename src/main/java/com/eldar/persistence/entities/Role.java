package com.eldar.persistence.entities;

import com.eldar.utils.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author  caito Vilas
 * date 08/2021
 * class Role
 */
@Entity
@Table(name = "roles")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter
@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @Override
    public String getAuthority() {
        return role.name();
    }
}
