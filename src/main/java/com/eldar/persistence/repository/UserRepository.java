package com.eldar.persistence.repository;

import com.eldar.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM UserEntity u WHERE u.id <> :id AND u.email = :email")
    Optional<UserEntity> userForEmail(Long id, String email);
}
