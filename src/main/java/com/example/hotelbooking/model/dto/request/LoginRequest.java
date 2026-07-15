package com.example.hotelbooking.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequest {
    private String UsernameorEmail;
    private String password;
}
