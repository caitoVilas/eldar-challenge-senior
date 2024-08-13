package com.eldar.persistence.entities;

import com.eldar.services.listeners.AuditProductListener;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author caito Vilas
 * date: 08/2024
 * Product entity
 */
@Entity
@Table(name = "products")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter
@Builder
@EntityListeners(AuditProductListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
