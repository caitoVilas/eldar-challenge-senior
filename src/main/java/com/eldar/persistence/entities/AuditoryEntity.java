package com.eldar.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditory")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter
@Builder
public class AuditoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String operation;
    private String user;
    private LocalDateTime date;
}
