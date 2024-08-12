package com.eldar.persistence.repository;

import com.eldar.persistence.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @uthor caito Vilas
 * date 08/2024
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
