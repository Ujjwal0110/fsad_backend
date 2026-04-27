package com.sams.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "activity_id"}))
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @Column(updatable = false)
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.REGISTERED;

    public Registration() {}

    public Registration(Long id, User student, Activity activity, LocalDateTime registrationDate, Status status) {
        this.id = id;
        this.student = student;
        this.activity = activity;
        this.registrationDate = registrationDate;
        this.status = status != null ? status : Status.REGISTERED;
    }

    @PrePersist
    protected void onCreate() {
        if (registrationDate == null) {
            registrationDate = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    public Activity getActivity() { return activity; }
    public void setActivity(Activity activity) { this.activity = activity; }
    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public enum Status {
        REGISTERED, ATTENDED, CANCELLED
    }

    // Manual Builder
    public static RegistrationBuilder builder() {
        return new RegistrationBuilder();
    }

    public static class RegistrationBuilder {
        private Long id;
        private User student;
        private Activity activity;
        private LocalDateTime registrationDate;
        private Status status = Status.REGISTERED;

        public RegistrationBuilder id(Long id) { this.id = id; return this; }
        public RegistrationBuilder student(User student) { this.student = student; return this; }
        public RegistrationBuilder activity(Activity activity) { this.activity = activity; return this; }
        public RegistrationBuilder registrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; return this; }
        public RegistrationBuilder status(Status status) { this.status = status; return this; }

        public Registration build() {
            return new Registration(id, student, activity, registrationDate, status);
        }
    }
}
