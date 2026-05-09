package com.yourname.bookstore.config;

import com.yourname.bookstore.entity.User;
import com.yourname.bookstore.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Ensure Admin exists with correct password
            User admin = userRepository.findByUsername("admin").orElse(new User());
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
            System.out.println("Admin account ready: admin / admin123");

            // Ensure User exists with correct password
            User user = userRepository.findByUsername("user").orElse(new User());
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("ROLE_USER");
            userRepository.save(user);
            System.out.println("User account ready: user / user123");

            // Print all users for debugging
            System.out.println("--- Current Users in Database ---");
            userRepository.findAll().forEach(u -> {
                System.out.println("ID: " + u.getId() + ", Username: [" + u.getUsername() + "], Role: [" + u.getRole() + "], Password Hash: [" + u.getPassword() + "]");
            });
            System.out.println("---------------------------------");
        };
    }
}
