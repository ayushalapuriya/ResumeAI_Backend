package com.auth.service;

import com.auth.dto.*;
import com.auth.entity.User;
import com.auth.repository.UserRepository;
import com.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .isActive(true)
                .subscriptionPlan("FREE")
                .build();

        repo.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getSubscriptionPlan()
        );

    }

    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getSubscriptionPlan()
        );

    }
    @Override
    public boolean validateToken(String token) {

        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            String username = jwtService.extractUsername(token);
            UserDetails user = userDetailsService.loadUserByUsername(username);

            return jwtService.isValid(token, user);

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String refreshToken(String token) {

        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            String username = jwtService.extractUsername(token);

            if (username == null) {
                throw new RuntimeException("Invalid token");
            }

            UserDetails user = userDetailsService.loadUserByUsername(username);

            if (!jwtService.isValid(token, user)) {
                throw new RuntimeException("Token is invalid or expired");
            }

            return jwtService.generateToken(repo.findByEmail(username).orElseThrow(()-> new RuntimeException("corresponding user not found")));

        } catch (Exception e) {
            throw new RuntimeException("Unable to refresh token");
        }
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user =  repo.findByUserId(id).orElseThrow(()->new RuntimeException("user not found"));
        return new UserResponse(user.getFullName(), user.getEmail());
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user =  repo.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));
        return new UserResponse(user.getFullName(), user.getEmail());
    }

    @Override
    public UpdateProfileRequest updateProfile(Integer id, UpdateProfileRequest updateProfile) {
        User existingUser = repo.findByUserId(id).orElseThrow(()->new RuntimeException("user not found"));

        existingUser.setFullName(updateProfile.getFullName());
        existingUser.setEmail(updateProfile.getEmail());
        existingUser.setPhone(updateProfile.getPhone());
        existingUser.setProfilePhoto(updateProfile.getProfilePhoto());
        existingUser.setJobTitle(updateProfile.getJobTitle());
        existingUser.setLocation(updateProfile.getLocation());
        repo.save(existingUser);
        return updateProfile;
    }

    @Override
    public void changePassword(Integer id, String newPassword) {

        User user = repo.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String encodedPassword = encoder.encode(newPassword);

        user.setPassword(encodedPassword);

        repo.save(user);
    }

    @Override
    public void updateSubscription(Integer id, String plan) {
        User user = repo.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setSubscriptionPlan(plan);

        repo.save(user);
    }

    @Override
    public java.util.List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public java.util.List<String> getAllEmails() {
        return repo.findAll().stream()
                .map(User::getEmail)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public void updateUserStatus(Integer id, boolean active) {
        User user = repo.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(active);
        repo.save(user);
    }

    @Override
    public void updateUserRole(Integer id, String role) {
        User user = repo.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        repo.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = repo.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found"));
        
        try {
            repo.delete(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user. They might have existing resumes or related data. Error: " + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> getUserGrowthData(String groupBy) {
        List<User> users = repo.findAll();
        
        // Group users by the formatted date string
        Map<String, Long> counts = users.stream()
            .filter(u -> u.getCreatedAt() != null)
            .collect(Collectors.groupingBy(u -> {
                LocalDate date = u.getCreatedAt().toLocalDate();
                if ("week".equalsIgnoreCase(groupBy)) {
                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    int week = date.get(weekFields.weekOfWeekBasedYear());
                    return date.getYear() + "-W" + String.format("%02d", week);
                } else if ("month".equalsIgnoreCase(groupBy)) {
                    return date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                } else {
                    return date.toString(); // Default to "day" (yyyy-MM-dd)
                }
            }, TreeMap::new, Collectors.counting()));

        List<Map<String, Object>> growthData = new ArrayList<>();
        long cumulativeUsers = 0;

        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            cumulativeUsers += entry.getValue();
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("date", entry.getKey());
            dataPoint.put("newUsers", entry.getValue());
            dataPoint.put("users", cumulativeUsers); // Cumulative total
            growthData.add(dataPoint);
        }

        return growthData;
    }
}
