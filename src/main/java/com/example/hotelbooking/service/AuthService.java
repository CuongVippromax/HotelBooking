package com.example.hotelbooking.service;

import com.example.hotelbooking.model.dto.request.LoginRequest;
import com.example.hotelbooking.model.dto.response.TokenResponse;

public interface AuthService {

    TokenResponse login(LoginRequest loginRequest);

    TokenResponse getRefreshToken(String refreshToken);

    void logout(String refreshToken);
}
