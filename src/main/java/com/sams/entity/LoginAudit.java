package com.sams.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_audit")
public class LoginAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private boolean success;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    public LoginAudit() {}

    public LoginAudit(Long id, String email, String role, boolean success, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.success = success;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static LoginAuditBuilder builder() {
        return new LoginAuditBuilder();
    }

    public static class LoginAuditBuilder {
        private Long id;
        private String email;
        private String role;
        private boolean success;
        private LocalDateTime createdAt;

        public LoginAuditBuilder id(Long id) { this.id = id; return this; }
        public LoginAuditBuilder email(String email) { this.email = email; return this; }
        public LoginAuditBuilder role(String role) { this.role = role; return this; }
        public LoginAuditBuilder success(boolean success) { this.success = success; return this; }
        public LoginAuditBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public LoginAudit build() {
            return new LoginAudit(id, email, role, success, createdAt);
        }
    }
}
