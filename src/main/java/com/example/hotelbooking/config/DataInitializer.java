package com.example.hotelbooking.config;

import com.example.hotelbooking.model.entity.User;
import com.example.hotelbooking.model.enums.UserRole;
import com.example.hotelbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "DATA_INITIALIZER")
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email:admin@hotelbooking.com}")
    private String adminEmail;

    @Value("${app.admin.password:Admin@12345}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        // Seed tài khoản ADMIN đầu tiên để các API cần quyền quản trị dùng được ngay.
        // Idempotent: nếu email admin đã tồn tại thì bỏ qua.
        if (userRepository.existsByEmail(adminEmail)) {
            log.info("Admin account already exists, skipping seed: {}", adminEmail);
            return;
        }

        User admin = User.builder()
                .firstName("System")
                .lastName("Admin")
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role(UserRole.ADMIN)
                .enabled(true)
                .build();

        userRepository.save(admin);
        log.info("Seeded default ADMIN account: {}", adminEmail);
    }
}
