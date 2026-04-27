package com.sams.service;

import com.sams.dto.ActivityDTO;
import com.sams.entity.Activity;
import com.sams.entity.Activity.Category;
import com.sams.entity.User;
import com.sams.exception.ResourceNotFoundException;
import com.sams.repository.ActivityRepository;
import com.sams.repository.RegistrationRepository;
import com.sams.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;

    public List<ActivityDTO> getAllActivities(String category, String search, String userEmail) {
        Category cat = null;
        if (category != null && !category.isBlank() && !category.equalsIgnoreCase("all")) {
            try {
                cat = Category.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Ignore invalid category and show all
            }
        }

        User currentUser = userEmail != null ? userRepository.findByEmail(userEmail).orElse(null) : null;
        String normalizedSearch = (search != null && !search.isBlank()) ? search.trim() : null;

        List<Activity> activities;
        if (cat != null && normalizedSearch != null) {
            activities = activityRepository.findByCategoryAndNameContainingIgnoreCase(cat, normalizedSearch);
        } else if (cat != null) {
            activities = activityRepository.findByCategory(cat);
        } else if (normalizedSearch != null) {
            activities = activityRepository.findByNameContainingIgnoreCase(normalizedSearch);
        } else {
            activities = activityRepository.findAll();
        }

        return activities.stream()
            .map(a -> toDTO(a, currentUser))
            .collect(Collectors.toList());
    }

    public ActivityDTO getById(Long id, String userEmail) {
        Activity activity = activityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));
        User currentUser = userEmail != null ? userRepository.findByEmail(userEmail).orElse(null) : null;
        return toDTO(activity, currentUser);
    }

    public ActivityDTO create(ActivityDTO dto, String adminEmail) {
        User admin = userRepository.findByEmail(adminEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        Activity activity = new Activity();
        activity.setName(dto.getName());
        activity.setDescription(dto.getDescription());
        activity.setCategory(dto.getCategory());
        activity.setActivityDate(dto.getActivityDate());
        activity.setActivityTime(dto.getActivityTime());
        activity.setLocation(dto.getLocation());
        activity.setMaxParticipants(dto.getMaxParticipants());
        activity.setCreatedBy(admin);

        return toDTO(activityRepository.save(activity), admin);
    }

    public ActivityDTO update(Long id, ActivityDTO dto) {
        Activity activity = activityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));

        activity.setName(dto.getName());
        activity.setDescription(dto.getDescription());
        activity.setCategory(dto.getCategory());
        activity.setActivityDate(dto.getActivityDate());
        activity.setActivityTime(dto.getActivityTime());
        activity.setLocation(dto.getLocation());
        activity.setMaxParticipants(dto.getMaxParticipants());

        return toDTO(activityRepository.save(activity), null);
    }

    public void delete(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Activity not found with id: " + id);
        }
        activityRepository.deleteById(id);
    }

    private ActivityDTO toDTO(Activity a, User currentUser) {
        int count = a.getRegistrations() != null ? a.getRegistrations().size() : 0;
        boolean registered = currentUser != null &&
            registrationRepository.existsByStudentAndActivity(currentUser, a);

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
            .registered(registered)
            .build();
    }
}
