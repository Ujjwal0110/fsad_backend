package com.sams.service;

import com.sams.dto.ActivityDTO;
import com.sams.entity.*;
import com.sams.exception.ResourceNotFoundException;
import com.sams.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public Map<String, Object> register(Long activityId, String studentEmail) {
        User student = userRepository.findByEmail(studentEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Activity activity = activityRepository.findById(activityId)
            .orElseThrow(() -> new ResourceNotFoundException("Activity not found"));

        if (registrationRepository.existsByStudentAndActivity(student, activity)) {
            throw new IllegalArgumentException("Already registered for this activity");
        }

        int count = activity.getRegistrations().size();
        if (count >= activity.getMaxParticipants()) {
            throw new IllegalArgumentException("Activity is full. No available slots.");
        }

        Registration reg = Registration.builder()
            .student(student)
            .activity(activity)
            .status(Registration.Status.REGISTERED)
            .build();

        registrationRepository.save(reg);
        return Map.of("message", "Successfully registered for " + activity.getName());
    }

    public Map<String, Object> cancel(Long activityId, String studentEmail) {
        User student = userRepository.findByEmail(studentEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Activity activity = activityRepository.findById(activityId)
            .orElseThrow(() -> new ResourceNotFoundException("Activity not found"));

        Registration reg = registrationRepository.findByStudentAndActivity(student, activity)
            .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));

        registrationRepository.delete(reg);
        return Map.of("message", "Registration cancelled successfully");
    }

    public List<ActivityDTO> getMyActivities(String studentEmail) {
        User student = userRepository.findByEmail(studentEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return registrationRepository.findByStudent(student).stream()
            .map(r -> {
                Activity a = r.getActivity();
                int count = a.getRegistrations() != null ? a.getRegistrations().size() : 0;
                return ActivityDTO.builder()
                    .id(a.getId())
                    .name(a.getName())
                    .description(a.getDescription())
                    .category(a.getCategory())
                    .activityDate(a.getActivityDate())
                    .activityTime(a.getActivityTime())
                    .location(a.getLocation())
                    .maxParticipants(a.getMaxParticipants())
                    .currentParticipants(count)
                    .availableSlots(a.getMaxParticipants() - count)
                    .createdByName(a.getCreatedBy() != null ? a.getCreatedBy().getName() : "Admin")
                    .createdAt(a.getCreatedAt())
                    .registered(true)
                    .build();
            }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getRegistrationsForActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
            .orElseThrow(() -> new ResourceNotFoundException("Activity not found"));

        return registrationRepository.findByActivity(activity).stream()
            .map(r -> {
                Map<String, Object> map = new java.util.HashMap<>();
                map.put("id", r.getId());
                map.put("studentName", r.getStudent().getName());
                map.put("studentEmail", r.getStudent().getEmail());
                map.put("enrollmentNo", r.getStudent().getEnrollmentNo() != null ? r.getStudent().getEnrollmentNo() : "");
                map.put("department", r.getStudent().getDepartment() != null ? r.getStudent().getDepartment() : "");
                map.put("status", r.getStatus().name());
                map.put("registrationDate", r.getRegistrationDate().toString());
                return map;
            })
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllRegistrationsForAdmin(Long activityId) {
        List<Registration> registrations;

        if (activityId != null) {
            Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found"));
            registrations = registrationRepository.findByActivity(activity);
        } else {
            registrations = registrationRepository.findAll();
        }

        return registrations.stream()
            .sorted(Comparator.comparing(Registration::getRegistrationDate).reversed())
            .map(r -> {
                Map<String, Object> map = new java.util.HashMap<>();
                map.put("id", r.getId());
                map.put("studentName", r.getStudent().getName());
                map.put("studentEmail", r.getStudent().getEmail());
                map.put("enrollmentNo", r.getStudent().getEnrollmentNo() != null ? r.getStudent().getEnrollmentNo() : "");
                map.put("department", r.getStudent().getDepartment() != null ? r.getStudent().getDepartment() : "");
                map.put("activityId", r.getActivity().getId());
                map.put("activityName", r.getActivity().getName());
                map.put("activityCategory", r.getActivity().getCategory().name());
                map.put("status", r.getStatus().name());
                map.put("registrationDate", r.getRegistrationDate().toString());
                return map;
            })
            .collect(Collectors.toList());
    }
}
