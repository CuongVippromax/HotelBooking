package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.model.dto.request.LoginRequest;
import com.example.hotelbooking.model.dto.response.TokenResponse;
import com.example.hotelbooking.service.AuthService;

public class AuthServiceImpl implements AuthService {
    @Override
    public TokenResponse getAccessToken(LoginRequest loginRequest) {

        return null;
    }

    @Override
    public TokenResponse getRefreshToken(String request) {
        return null;
    }
}
