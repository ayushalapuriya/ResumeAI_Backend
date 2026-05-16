package com.auth.service;

import com.auth.dto.*;
import com.auth.entity.User;

public interface AuthService {

    // 🔐 Register new user
    AuthResponse register(RegisterRequest user);

    // 🔑 Login user → returns JWT token
    AuthResponse login(LoginRequest user);

    // ✅ Validate JWT token
    boolean validateToken(String token);

    // 🔄 Refresh JWT token
    String refreshToken(String token);

    // 👤 Get user by ID
    UserResponse getUserById(Integer id);

    // 📧 Get user by Email
    UserResponse getUserByEmail(String email);

    // ✏️ Update profile
    UpdateProfileRequest updateProfile(Integer id, UpdateProfileRequest user);

    // 🔒 Change password
    void changePassword(Integer id, String newPassword);

    // 💳 Update subscription plan
    void updateSubscription(Integer id, String plan);

    // 👨‍💼 Admin: Get all users
    java.util.List<User> getAllUsers();

    // 👨‍💼 Admin: Get all emails
    java.util.List<String> getAllEmails();

    // 👨‍💼 Admin: Update user status
    void updateUserStatus(Integer id, boolean active);

    // 👨‍💼 Admin: Update user role
    void updateUserRole(Integer id, String role);

    // 👨‍💼 Admin: Delete user
    void deleteUser(Integer id);

    // 📈 Admin: Analytics
    java.util.List<java.util.Map<String, Object>> getUserGrowthData(String groupBy);
}
