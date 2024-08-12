package com.eldar.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;
}
