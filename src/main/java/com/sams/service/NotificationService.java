package com.sams.service;

import com.sams.dto.NotificationDTO;
import com.sams.entity.*;
import com.sams.exception.ResourceNotFoundException;
import com.sams.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationDTO sendBroadcast(String title, String message, String adminEmail) {
        User admin = userRepository.findByEmail(adminEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        Notification notification = Notification.builder()
            .title(title)
            .message(message)
            .createdBy(admin)
            .targetStudent(null)
            .build();

        return toDTO(notificationRepository.save(notification));
    }

    public List<NotificationDTO> getForCurrentUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return notificationRepository.findForStudent(user).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreatedAtDesc().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public Map<String, Object> markAsRead(Long id) {
        Notification notif = notificationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        notif.setRead(true);
        notificationRepository.save(notif);
        return Map.of("message", "Notification marked as read");
    }

    public long getUnreadCount(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return notificationRepository.countUnreadForStudent(user);
    }

    private NotificationDTO toDTO(Notification n) {
        return NotificationDTO.builder()
            .id(n.getId())
            .title(n.getTitle())
            .message(n.getMessage())
            .createdByName(n.getCreatedBy() != null ? n.getCreatedBy().getName() : "System")
            .targetStudentId(n.getTargetStudent() != null ? n.getTargetStudent().getId() : null)
            .read(n.isRead())
            .createdAt(n.getCreatedAt())
            .build();
    }
}
