package com.eldar.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author  caito Vilas
 * date 08/2024
 * description class UserEntity
 */
@Entity
@Table(name = "users")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter
@Builder
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String telephone;
    private String address;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) return null;
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getRole().name())).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
