package com.sams.controller;

import com.sams.dto.ActivityDTO;
import com.sams.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/{activityId}")
    public ResponseEntity<Map<String, Object>> register(
            @PathVariable Long activityId, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(registrationService.register(activityId, auth.getName()));
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<Map<String, Object>> cancel(
            @PathVariable Long activityId, Authentication auth) {
        return ResponseEntity.ok(registrationService.cancel(activityId, auth.getName()));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ActivityDTO>> getMyActivities(Authentication auth) {
        return ResponseEntity.ok(registrationService.getMyActivities(auth.getName()));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getAllRegistrationsForAdmin(
            @RequestParam(required = false) Long activityId) {
        return ResponseEntity.ok(registrationService.getAllRegistrationsForAdmin(activityId));
    }
}
