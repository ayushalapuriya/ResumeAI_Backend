package com.auth.controller;

import com.auth.dto.AuthResponse;
import com.auth.dto.LoginRequest;
import com.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testLogin_Success() {
        // Arrange
        LoginRequest request = new LoginRequest("test@example.com", "password");
        AuthResponse response = AuthResponse.builder()
                .token("mock-token")
                .email("test@example.com")
                .build();

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        // Act
        AuthResponse result = authController.login(request);

        // Assert
        assertNotNull(result);
        assertEquals("mock-token", result.getToken());
        assertEquals("test@example.com", result.getEmail());
    }
}
