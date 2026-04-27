package com.sams.dto;

import java.time.LocalDateTime;

public class NotificationDTO {
    private Long id;
    private String title;
    private String message;
    private String createdByName;
    private Long targetStudentId;
    private boolean read;
    private LocalDateTime createdAt;

    public NotificationDTO() {}

    public NotificationDTO(Long id, String title, String message, String createdByName, Long targetStudentId, boolean read, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdByName = createdByName;
        this.targetStudentId = targetStudentId;
        this.read = read;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getCreatedByName() { return createdByName; }
    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }
    public Long getTargetStudentId() { return targetStudentId; }
    public void setTargetStudentId(Long targetStudentId) { this.targetStudentId = targetStudentId; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Manual Builder
    public static NotificationDTOBuilder builder() {
        return new NotificationDTOBuilder();
    }

    public static class NotificationDTOBuilder {
        private Long id;
        private String title;
        private String message;
        private String createdByName;
        private Long targetStudentId;
        private boolean read;
        private LocalDateTime createdAt;

        public NotificationDTOBuilder id(Long id) { this.id = id; return this; }
        public NotificationDTOBuilder title(String title) { this.title = title; return this; }
        public NotificationDTOBuilder message(String message) { this.message = message; return this; }
        public NotificationDTOBuilder createdByName(String createdByName) { this.createdByName = createdByName; return this; }
        public NotificationDTOBuilder targetStudentId(Long targetStudentId) { this.targetStudentId = targetStudentId; return this; }
        public NotificationDTOBuilder read(boolean read) { this.read = read; return this; }
        public NotificationDTOBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public NotificationDTO build() {
            return new NotificationDTO(id, title, message, createdByName, targetStudentId, read, createdAt);
        }
    }
}
