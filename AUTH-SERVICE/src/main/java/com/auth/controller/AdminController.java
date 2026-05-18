package com.auth.controller;

import com.auth.entity.User;
import com.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;
    private final com.auth.client.TemplateClient templateClient;
    private final com.auth.client.ResumeClient resumeClient;

    // 👥 Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    // ➕ Create User
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(authService.createUser(body));
    }

    // 📧 Get all emails (for broadcast)
    @GetMapping("/users/emails")
    public ResponseEntity<List<String>> getAllEmails() {
        return ResponseEntity.ok(authService.getAllEmails());
    }

    // 🚫 Suspend User
    @PutMapping("/users/{id}/suspend")
    public ResponseEntity<String> suspendUser(@PathVariable Integer id) {
        authService.updateUserStatus(id, false);
        return ResponseEntity.ok("User suspended");
    }

    // ✅ Reactivate User
    @PutMapping("/users/{id}/reactivate")
    public ResponseEntity<String> reactivateUser(@PathVariable Integer id) {
        authService.updateUserStatus(id, true);
        return ResponseEntity.ok("User reactivated");
    }

    // 🏷️ Update Role
    @PutMapping("/users/{id}/role")
    public ResponseEntity<String> updateRole(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        authService.updateUserRole(id, body.get("role"));
        return ResponseEntity.ok("Role updated");
    }

    // 💳 Update Subscription Plan
    @PutMapping("/users/{id}/subscription")
    public ResponseEntity<String> updateSubscription(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        authService.updateSubscription(id, body.get("plan"));
        return ResponseEntity.ok("Subscription updated");
    }

    // 🗑️ Delete User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        authService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        List<User> users = authService.getAllUsers();
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", users.size());
        stats.put("premiumUsers", users.stream().filter(u -> "PREMIUM".equals(u.getSubscriptionPlan())).count());
        stats.put("activeUsers", users.stream().filter(User::isActive).count());
        
        try {
            stats.put("totalTemplates", templateClient.getTemplateCount());
        } catch (Exception e) {
            stats.put("totalTemplates", 0);
        }
        
        try {
            stats.put("totalResumes", resumeClient.getResumeCount());
        } catch (Exception e) {
            stats.put("totalResumes", 0);
        }
        
        // Real stats for today
        java.time.LocalDateTime startOfToday = java.time.LocalDateTime.now().with(java.time.LocalTime.MIN);
        long newUsersToday = users.stream()
                .filter(u -> u.getCreatedAt() != null && u.getCreatedAt().isAfter(startOfToday))
                .count();
        stats.put("newUsersToday", newUsersToday);

        // Calculate growth percentage (new users in last 30 days vs total)
        java.time.LocalDateTime thirtyDaysAgo = java.time.LocalDateTime.now().minusDays(30);
        long newUsersLast30Days = users.stream()
                .filter(u -> u.getCreatedAt() != null && u.getCreatedAt().isAfter(thirtyDaysAgo))
                .count();
        
        double growthPercentage = users.size() > 0 ? ((double) newUsersLast30Days / users.size()) * 100 : 0;
        stats.put("growthPercentage", String.format("%.1f", growthPercentage));

        return ResponseEntity.ok(stats);
    }

    // 📝 Audit Logs (Mocked)
    @GetMapping("/audit-logs")
    public ResponseEntity<List<Map<String, String>>> getAuditLogs() {
        return ResponseEntity.ok(List.of(
            Map.of("id", "1", "action", "USER_LOGIN", "performedBy", "admin@resumeai.com", "timestamp", "2024-05-16T10:00:00"),
            Map.of("id", "2", "action", "TEMPLATE_CREATED", "performedBy", "admin@resumeai.com", "timestamp", "2024-05-16T11:30:00"),
            Map.of("id", "3", "action", "USER_SUSPENDED", "performedBy", "admin@resumeai.com", "targetUser", "john@doe.com", "timestamp", "2024-05-16T12:00:00")
        ));
    }

    // 📈 Analytics (Real)
    @GetMapping("/analytics/user-growth")
    public ResponseEntity<List<Map<String, Object>>> getUserGrowth(@RequestParam(defaultValue = "day") String groupBy) {
        return ResponseEntity.ok(authService.getUserGrowthData(groupBy));
    }

    @GetMapping("/analytics/platform")
    public ResponseEntity<Map<String, Object>> getPlatformAnalytics() {
        return ResponseEntity.ok(Map.of(
            "activeSessions", 45,
            "serverStatus", "Healthy",
            "uptime", "12d 4h"
        ));
    }

    @GetMapping("/analytics/ai-usage")
    public ResponseEntity<Map<String, Object>> getAiUsageStats() {
        return ResponseEntity.ok(Map.of(
            "totalGenerations", 1250,
            "successRate", "98.5%",
            "tokensUsed", 450000
        ));
    }
}
