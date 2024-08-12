package com.eldar.persistence.repository;

import com.eldar.persistence.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author caito Vilas
 * date: 08/2024
 * ProductRepository
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
}
