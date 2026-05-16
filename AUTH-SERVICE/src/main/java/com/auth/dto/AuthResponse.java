package com.auth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private Integer userId;
    private String fullName;
    private String email;
    private String role;
    private String subscriptionPlan;

    // Explicit constructor to avoid Lombok issues
    public AuthResponse(String token, Integer userId, String fullName, String email, String role, String subscriptionPlan) {
        this.token = token;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.subscriptionPlan = subscriptionPlan;
    }

    // Helper for legacy support if needed
    public AuthResponse(String token) {
        this.token = token;
    }
}
