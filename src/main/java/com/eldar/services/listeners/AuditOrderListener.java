package com.eldar.services.listeners;

import com.eldar.persistence.entities.AuditoryEntity;
import com.eldar.persistence.entities.Order;
import com.eldar.persistence.repository.AuditOrderRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

/**
 * @author caito Vilas
 * Date 08/2024
 * Description class AuditOrderListener
 */
@RequiredArgsConstructor(onConstructor_ = @__(@Lazy))
public class AuditOrderListener {
    private final AuditOrderRepository auditOrderRepository;

    /**
     * Method to persist the audit of the order
     * @param order
     */
    @PrePersist
    private void perist(Order order){
        var history = this.generateAudit(order,"INSERT");
        auditOrderRepository.save(history);

    }

    /**
     * Method to update the audit of the order
     * @param order
     */
    @PreUpdate
    private void update(Order order){
        var history = this.generateAudit(order, "UPDATE");
        auditOrderRepository.save(history);
    }

    /**
     * Method to remove the audit of the order
     * @param order
     */
    @PreRemove
    private void remove(Order order){
        var history = this.generateAudit(order, "DELETE");
        auditOrderRepository.save(history);
    }

    /**
     * Method to generate the audit of the order
     * @param order
     * @param operation
     * @return
     */
    private AuditoryEntity generateAudit(Order order, String operation){
        var userSys = SecurityContextHolder.getContext().getAuthentication().getName();
        return AuditoryEntity.builder()
                .name(order.getId().toString())
                .date(LocalDateTime.now())
                .operation(operation)
                .user(userSys)
                .build();
    }
}
