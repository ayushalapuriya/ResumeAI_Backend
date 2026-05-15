package org.app.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.app.notificationservice.entity.Notification;
import org.app.notificationservice.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationResource {

    private final NotificationService service;

    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<Notification>> getByRecipient(@PathVariable Integer recipientId) {
        return ResponseEntity.ok(service.getByRecipient(recipientId));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        service.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/recipient/{recipientId}/read-all")
    public ResponseEntity<Void> markAllRead(@PathVariable Integer recipientId) {
        service.markAllRead(recipientId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recipient/{recipientId}/unread-count")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Integer recipientId) {
        return ResponseEntity.ok(service.getUnreadCount(recipientId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteNotification(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<Void> sendBulk(@RequestBody List<Notification> notifications) {
        service.sendBulk(notifications);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // ⚡ Broadcast endpoint for admin (custom addition for your dashboard)
    @PostMapping("/broadcast")
    public ResponseEntity<Notification> broadcast(@RequestBody Map<String, String> body) {
        Notification broadcast = Notification.builder()
                .title("System Broadcast")
                .message(body.get("message"))
                .type("BROADCAST")
                .channel("BOTH")
                .recipientId(null) // null means all
                .build();
        return ResponseEntity.ok(service.send(broadcast));
    }
}
