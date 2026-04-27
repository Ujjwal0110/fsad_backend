package com.sams.dto;

public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String role; // ADMIN or STUDENT
    private String department;
    private String enrollmentNo;
    private String phone;

    public SignupRequest() {}

    public SignupRequest(String name, String email, String password, String role, String department, String enrollmentNo, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.department = department;
        this.enrollmentNo = enrollmentNo;
        this.phone = phone;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getEnrollmentNo() { return enrollmentNo; }
    public void setEnrollmentNo(String enrollmentNo) { this.enrollmentNo = enrollmentNo; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
