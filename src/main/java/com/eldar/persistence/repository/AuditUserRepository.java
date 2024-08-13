package com.eldar.persistence.repository;

import com.eldar.persistence.entities.AuditoryEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author caito Vilas
 * date: 08/2024
 * AuditUserRepository
 */
public interface AuditUserRepository extends CrudRepository<AuditoryEntity, Long> {
}
