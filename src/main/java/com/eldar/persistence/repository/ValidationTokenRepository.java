package com.eldar.persistence.repository;

import com.eldar.persistence.entities.ValidationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * @author caito Vilas
 * date: 08/2024
 * Repository interface for validation token
 */
public interface ValidationTokenRepository extends JpaRepository<ValidationToken, Long> {
    Optional<ValidationToken> findByToken(String token);
}
