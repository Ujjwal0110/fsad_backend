package com.sams.service;

import com.sams.entity.*;
import com.sams.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final ActivityRepository activityRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final LoginAuditRepository loginAuditRepository;

    public Map<String, Object> getAdminDashboard() {
        long totalActivities = activityRepository.count();
        long totalStudents = userRepository.findAll().stream()
            .filter(u -> u.getRole() == User.Role.STUDENT).count();
        long totalRegistrations = registrationRepository.count();
        long totalNotifications = notificationRepository.count();
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        long adminLogins24h = loginAuditRepository.countByRoleAndSuccessAndCreatedAtAfter("ADMIN", true, since);
        long failedAdminLogins24h = loginAuditRepository.countByRoleAndSuccessAndCreatedAtAfter("ADMIN", false, since);
        LocalDateTime lastAdminLoginAt = loginAuditRepository
            .findTopByRoleAndSuccessOrderByCreatedAtDesc("ADMIN", true)
            .map(LoginAudit::getCreatedAt)
            .orElse(null);

        // Top 3 activities by registration count
        List<Map<String, Object>> topActivities = activityRepository.findAll().stream()
            .sorted((a, b) -> Integer.compare(
                b.getRegistrations() != null ? b.getRegistrations().size() : 0,
                a.getRegistrations() != null ? a.getRegistrations().size() : 0))
            .limit(3)
            .map(a -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("id", a.getId());
                map.put("name", a.getName());
                map.put("category", a.getCategory().name());
                map.put("registrations", a.getRegistrations() != null ? a.getRegistrations().size() : 0);
                map.put("maxParticipants", a.getMaxParticipants());
                return map;
            })
            .collect(Collectors.toList());

        // Category breakdown
        Map<String, Long> categoryBreakdown = activityRepository.findAll().stream()
            .collect(Collectors.groupingBy(a -> a.getCategory().name(), Collectors.counting()));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalActivities", totalActivities);
        result.put("totalStudents", totalStudents);
        result.put("totalRegistrations", totalRegistrations);
        result.put("totalNotifications", totalNotifications);
        result.put("adminLogins24h", adminLogins24h);
        result.put("failedAdminLogins24h", failedAdminLogins24h);
        result.put("lastAdminLoginAt", lastAdminLoginAt);
        result.put("topActivities", topActivities);
        result.put("categoryBreakdown", categoryBreakdown);
        return result;
    }

    public Map<String, Object> getStudentDashboard(String email) {
        User student = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Registration> myRegs = registrationRepository.findByStudent(student);
        long unreadNotifications = notificationRepository.countUnreadForStudent(student);
        long totalActivities = activityRepository.count();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("myRegistrations", myRegs.size());
        result.put("totalActivities", totalActivities);
        result.put("unreadNotifications", unreadNotifications);
        result.put("studentName", student.getName());
        return result;
    }
}
