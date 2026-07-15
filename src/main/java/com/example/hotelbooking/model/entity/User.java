package com.example.hotelbooking.model.entity;

import com.example.hotelbooking.model.base.BaseEntity;
import com.example.hotelbooking.model.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true),
        @Index(name = "idx_user_role", columnList = "role"),
        @Index(name = "idx_user_enabled", columnList = "enabled")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true, length = 100)
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Column(nullable = false, length = 100, name = "password")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password hash must be between 8 and 100 characters")
    private String password;

    @Column(nullable = false, length = 50, name = "first_name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(nullable = false, length = 50, name = "last_name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(length = 20, name = "phone_number")
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Phone number must be 10-15 digits, optionally starting with +")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull(message = "User role is required")
    private UserRole role;

    @Column(nullable = false)
    @NotNull(message = "Enabled status is required")
    @Builder.Default
    private Boolean enabled = true;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role.name());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
