package com.eldar.services.listeners;

import com.eldar.persistence.entities.AuditoryEntity;
import com.eldar.persistence.entities.UserEntity;
import com.eldar.persistence.repository.AuditUserRepository;
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
 * Description class AuditUserListener
 */
@RequiredArgsConstructor(onConstructor_ = @__(@Lazy))
public class AuditUserListener {
    private final AuditUserRepository auditUserRepository;

    /**
     * Method to persist the audit of the user
     * @param user
     */
    @PrePersist
    private void perist(UserEntity user){
        var history = this.generateAudit(user, "INSERT");
        auditUserRepository.save(history);

    }

    /**
     * Method to update the audit of the user
     * @param user
     */
    @PreUpdate
    private void update(UserEntity user){
        var history = this.generateAudit(user, "UPDATE");
        auditUserRepository.save(history);
    }

    /**
     * Method to remove the audit of the user
     * @param user
     */
    @PreRemove
    private void remove(UserEntity user){
        var history = this.generateAudit(user, "DELETE");
        auditUserRepository.save(history);
    }

    /**
     * Method to generate the audit of the user
     * @param user
     * @param operation
     * @return
     */
    private AuditoryEntity generateAudit(UserEntity user, String operation){
        var userSys = SecurityContextHolder.getContext().getAuthentication().getName();
        return AuditoryEntity.builder()
                .name(user.getFullName())
                .date(LocalDateTime.now())
                .operation(operation)
                .user(userSys)
                .build();
    }
}
