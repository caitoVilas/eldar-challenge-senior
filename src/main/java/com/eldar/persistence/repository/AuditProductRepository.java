package com.eldar.persistence.repository;

import com.eldar.persistence.entities.AuditoryEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author caito Vilas
 * date: 08/2024
 * AuditProductRepository
 */
public interface AuditProductRepository extends CrudRepository<AuditoryEntity, Long> {
}
