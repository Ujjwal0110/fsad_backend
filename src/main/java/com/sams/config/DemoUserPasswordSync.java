package com.sams.config;

import com.sams.entity.User;
import com.sams.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DemoUserPasswordSync {

    private static final List<String> DEMO_EMAILS = List.of(
        "admin@sams.edu",
        "alice@student.edu",
        "bob@student.edu",
        "carol@student.edu"
    );

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void syncDemoPasswords() {
        for (String email : DEMO_EMAILS) {
            userRepository.findByEmail(email).ifPresent(user -> syncPassword(user));
        }
    }

    private void syncPassword(User user) {
        if (!passwordEncoder.matches("password", user.getPassword())) {
            user.setPassword(passwordEncoder.encode("password"));
            userRepository.save(user);
        }
    }
}
