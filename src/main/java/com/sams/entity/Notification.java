package com.sams.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    // null = broadcast to all students; non-null = targeted
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_student_id")
    private User targetStudent;

    private boolean isRead = false;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Notification() {}

    public Notification(Long id, String title, String message, User createdBy, User targetStudent, boolean isRead, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdBy = createdBy;
        this.targetStudent = targetStudent;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public User getTargetStudent() { return targetStudent; }
    public void setTargetStudent(User targetStudent) { this.targetStudent = targetStudent; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Manual Builder
    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

    public static class NotificationBuilder {
        private Long id;
        private String title;
        private String message;
        private User createdBy;
        private User targetStudent;
        private boolean isRead = false;
        private LocalDateTime createdAt;

        public NotificationBuilder id(Long id) { this.id = id; return this; }
        public NotificationBuilder title(String title) { this.title = title; return this; }
        public NotificationBuilder message(String message) { this.message = message; return this; }
        public NotificationBuilder createdBy(User createdBy) { this.createdBy = createdBy; return this; }
        public NotificationBuilder targetStudent(User targetStudent) { this.targetStudent = targetStudent; return this; }
        public NotificationBuilder isRead(boolean isRead) { this.isRead = isRead; return this; }
        public NotificationBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Notification build() {
            return new Notification(id, title, message, createdBy, targetStudent, isRead, createdAt);
        }
    }
}
