package com.eldar.persistence.entities;

import com.eldar.utils.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author caito Vilas
 * date 08/2024
 * Represents an order.
 */
@Entity
@Table(name = "orders")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> items;
}
