package com.example.hotelbooking.model.dto.response;

import com.example.hotelbooking.model.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    @Builder.Default
    private String tokenType = "Bearer";
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
}
