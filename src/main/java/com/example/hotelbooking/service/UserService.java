package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.DuplicateResourceException;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.dto.request.UserCreationRequest;
import com.example.hotelbooking.model.dto.response.UserResponse;
import com.example.hotelbooking.model.entity.User;
import com.example.hotelbooking.model.enums.UserRole;
import com.example.hotelbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "USER_SERVICE")
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "User with email '" + request.getEmail() + "' already exists");
        }

        User newUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(UserRole.CUSTOMER)
                .build();

        User saved = userRepository.save(newUser);
        log.info("Created user id={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        return toResponse(findUserOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = findUserOrThrow(id);
        userRepository.delete(user);
        log.info("Deleted user id={}", id);
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
