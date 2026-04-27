package com.sams.repository;

import com.sams.entity.LoginAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LoginAuditRepository extends JpaRepository<LoginAudit, Long> {
    long countByRoleAndSuccessAndCreatedAtAfter(String role, boolean success, LocalDateTime createdAt);

    Optional<LoginAudit> findTopByRoleAndSuccessOrderByCreatedAtDesc(String role, boolean success);
}
