package com.eldar.persistence.repository;

import com.eldar.persistence.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @uthor caito Vilas
 * date 08/2024
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
