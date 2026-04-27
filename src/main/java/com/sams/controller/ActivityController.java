package com.sams.controller;

import com.sams.dto.ActivityDTO;
import com.sams.service.ActivityService;
import com.sams.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getAll(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            Authentication auth) {
        String email = auth != null ? auth.getName() : null;
        return ResponseEntity.ok(activityService.getAllActivities(category, search, email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getById(@PathVariable Long id, Authentication auth) {
        String email = auth != null ? auth.getName() : null;
        return ResponseEntity.ok(activityService.getById(id, email));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ActivityDTO> create(@RequestBody ActivityDTO dto, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(activityService.create(dto, auth.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ActivityDTO> update(@PathVariable Long id, @RequestBody ActivityDTO dto) {
        return ResponseEntity.ok(activityService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        activityService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Activity deleted successfully"));
    }

    @GetMapping("/{id}/registrations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getRegistrations(@PathVariable Long id) {
        return ResponseEntity.ok(registrationService.getRegistrationsForActivity(id));
    }
}
