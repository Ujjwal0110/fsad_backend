package com.sams.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private LocalDate activityDate;

    private LocalTime activityTime;

    private String location;

    @Column(nullable = false)
    private Integer maxParticipants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registrations = new ArrayList<>();

    public Activity() {}

    public Activity(Long id, String name, String description, Category category, LocalDate activityDate, LocalTime activityTime, String location, Integer maxParticipants, User createdBy, LocalDateTime createdAt, List<Registration> registrations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.registrations = registrations != null ? registrations : new ArrayList<>();
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
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<Registration> getRegistrations() { return registrations; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; }

    public int getAvailableSlots() {
        return maxParticipants - (registrations == null ? 0 : registrations.size());
    }

    public enum Category {
        CLUB, SPORT, EVENT
    }

    // Manual Builder
    public static ActivityBuilder builder() {
        return new ActivityBuilder();
    }

    public static class ActivityBuilder {
        private Long id;
        private String name;
        private String description;
        private Category category;
        private LocalDate activityDate;
        private LocalTime activityTime;
        private String location;
        private Integer maxParticipants;
        private User createdBy;
        private LocalDateTime createdAt;
        private List<Registration> registrations;

        public ActivityBuilder id(Long id) { this.id = id; return this; }
        public ActivityBuilder name(String name) { this.name = name; return this; }
        public ActivityBuilder description(String description) { this.description = description; return this; }
        public ActivityBuilder category(Category category) { this.category = category; return this; }
        public ActivityBuilder activityDate(LocalDate activityDate) { this.activityDate = activityDate; return this; }
        public ActivityBuilder activityTime(LocalTime activityTime) { this.activityTime = activityTime; return this; }
        public ActivityBuilder location(String location) { this.location = location; return this; }
        public ActivityBuilder maxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; return this; }
        public ActivityBuilder createdBy(User createdBy) { this.createdBy = createdBy; return this; }
        public ActivityBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ActivityBuilder registrations(List<Registration> registrations) { this.registrations = registrations; return this; }

        public Activity build() {
            return new Activity(id, name, description, category, activityDate, activityTime, location, maxParticipants, createdBy, createdAt, registrations);
        }
    }
}
