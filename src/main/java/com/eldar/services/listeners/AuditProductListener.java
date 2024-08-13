package com.eldar.services.listeners;

import com.eldar.persistence.entities.AuditoryEntity;
import com.eldar.persistence.entities.Product;
import com.eldar.persistence.entities.UserEntity;
import com.eldar.persistence.repository.AuditProductRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

/**
 * @author  caito Vilas
 * Date 08/2024
 * Description class AuditProductListener
 */
@RequiredArgsConstructor(onConstructor_ = @__(@Lazy))
public class AuditProductListener {
    private final AuditProductRepository auditProductRepository;

    /**
     * Method to persist the audit of the product
     * @param product
     */
    @PrePersist
    private void perist(Product product){
        var history = this.generateAudit(product,"INSERT");
        auditProductRepository.save(history);

    }

    /**
     * Method to update the audit of the product
     * @param product
     */
    @PreUpdate
    private void update(Product product){
        var history = this.generateAudit(product, "UPDATE");
        auditProductRepository.save(history);
    }

    /**
     * Method to remove the audit of the product
     * @param product
     */
    @PreRemove
    private void remove(Product product){
        var history = this.generateAudit(product, "DELETE");
        auditProductRepository.save(history);
    }

    /**
     * Method to generate the audit of the product
     * @param product
     * @param operation
     * @return
     */
    private AuditoryEntity generateAudit(Product product, String operation){
        var userSys = SecurityContextHolder.getContext().getAuthentication().getName();
        return AuditoryEntity.builder()
                .name(product.getName())
                .date(LocalDateTime.now())
                .operation(operation)
                .user(userSys)
                .build();
    }
}
