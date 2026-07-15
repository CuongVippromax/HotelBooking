package com.example.hotelbooking.service;

import com.example.hotelbooking.model.dto.request.LoginRequest;
import com.example.hotelbooking.model.dto.response.LoginResponse;
import com.example.hotelbooking.model.dto.response.TokenResponse;
import com.example.hotelbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public interface AuthService {

    TokenResponse getAccessToken(LoginRequest loginRequest);

    TokenResponse getRefreshToken(String request);


}
