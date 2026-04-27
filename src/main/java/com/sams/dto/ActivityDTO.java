package com.sams.dto;

import com.sams.entity.Activity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class ActivityDTO {
    private Long id;
    private String name;
    private String description;
    private Category category;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate activityDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime activityTime;

    private String location;
    private Integer maxParticipants;
    private int currentParticipants;
    private int availableSlots;
    private String createdByName;
    private LocalDateTime createdAt;
    private boolean registered;

    public ActivityDTO() {}

    public ActivityDTO(Long id, String name, String description, Category category, LocalDate activityDate, LocalTime activityTime, String location, Integer maxParticipants, int currentParticipants, int availableSlots, String createdByName, LocalDateTime createdAt, boolean registered) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.currentParticipants = currentParticipants;
        this.availableSlots = availableSlots;
        this.createdByName = createdByName;
        this.createdAt = createdAt;
        this.registered = registered;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public LocalDate getActivityDate() { return activityDate; }
    public void setActivityDate(LocalDate activityDate) { this.activityDate = activityDate; }
    public LocalTime getActivityTime() { return activityTime; }
    public void setActivityTime(LocalTime activityTime) { this.activityTime = activityTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Integer getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }
    public int getCurrentParticipants() { return currentParticipants; }
    public void setCurrentParticipants(int currentParticipants) { this.currentParticipants = currentParticipants; }
    public int getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(int availableSlots) { this.availableSlots = availableSlots; }
    public String getCreatedByName() { return createdByName; }
    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public boolean isRegistered() { return registered; }
    public void setRegistered(boolean registered) { this.registered = registered; }

    // Manual Builder
    public static ActivityDTOBuilder builder() {
        return new ActivityDTOBuilder();
    }

    public static class ActivityDTOBuilder {
        private Long id;
        private String name;
        private String description;
        private Category category;
        private LocalDate activityDate;
        private LocalTime activityTime;
        private String location;
        private Integer maxParticipants;
        private int currentParticipants;
        private int availableSlots;
        private String createdByName;
        private LocalDateTime createdAt;
        private boolean registered;

        public ActivityDTOBuilder id(Long id) { this.id = id; return this; }
        public ActivityDTOBuilder name(String name) { this.name = name; return this; }
        public ActivityDTOBuilder description(String description) { this.description = description; return this; }
        public ActivityDTOBuilder category(Category category) { this.category = category; return this; }
        public ActivityDTOBuilder activityDate(LocalDate activityDate) { this.activityDate = activityDate; return this; }
        public ActivityDTOBuilder activityTime(LocalTime activityTime) { this.activityTime = activityTime; return this; }
        public ActivityDTOBuilder location(String location) { this.location = location; return this; }
        public ActivityDTOBuilder maxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; return this; }
        public ActivityDTOBuilder currentParticipants(int currentParticipants) { this.currentParticipants = currentParticipants; return this; }
        public ActivityDTOBuilder availableSlots(int availableSlots) { this.availableSlots = availableSlots; return this; }
        public ActivityDTOBuilder createdByName(String createdByName) { this.createdByName = createdByName; return this; }
        public ActivityDTOBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ActivityDTOBuilder registered(boolean registered) { this.registered = registered; return this; }

        public ActivityDTO build() {
            return new ActivityDTO(id, name, description, category, activityDate, activityTime, location, maxParticipants, currentParticipants, availableSlots, createdByName, createdAt, registered);
        }
    }
}
