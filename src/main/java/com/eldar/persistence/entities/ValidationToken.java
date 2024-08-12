package com.eldar.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author caito Vilas
 * date: 08/2024
 * Entity class for validation token
 */
@Entity
@Table(name = "validation_token")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter
@Builder
public class ValidationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expirationDate;
    private String email;
}
