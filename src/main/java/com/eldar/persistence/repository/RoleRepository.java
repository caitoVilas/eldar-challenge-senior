package com.eldar.persistence.repository;

import com.eldar.persistence.entities.Role;
import com.eldar.utils.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author  caito Vilas
 * Date 08/2021
 * Description class RoleRepository
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleName role);
}
