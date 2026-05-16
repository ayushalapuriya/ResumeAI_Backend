package com.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.app.client.AuthClient;
import com.app.dto.UserResponse;
import com.app.entity.Notification;
import com.app.repository.NotificationRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final JavaMailSender mailSender;
    private final AuthClient authClient;

    @Override
    public Notification send(Notification notification) {
        log.info("Sending notification to recipient: {}", notification.getRecipientId());
        
        Notification saved = repository.save(notification);

        // Handle Broadcast Emails
        if ("BROADCAST".equals(notification.getType()) && ("BOTH".equals(notification.getChannel()) || "EMAIL".equals(notification.getChannel()))) {
            sendBroadcastEmails(saved);
        } else if ("EMAIL".equals(notification.getChannel()) || "BOTH".equals(notification.getChannel())) {
            sendEmail(saved);
        }
        
        return saved;
    }

    @Override
    @Transactional
    public void sendBulk(List<Notification> notifications) {
        log.info("Sending bulk notifications, count: {}", notifications.size());
        repository.saveAll(notifications);
    }

    @Override
    public void markAsRead(Long notificationId) {
        repository.findById(notificationId).ifPresent(n -> {
            n.setRead(true);
            repository.save(n);
        });
    }

    @Override
    @Transactional
    public void markAllRead(Integer recipientId) {
        List<Notification> unread = repository.findByRecipientIdAndIsReadOrderBySentAtDesc(recipientId, false);
        unread.forEach(n -> n.setRead(true));
        repository.saveAll(unread);
    }

    @Override
    public List<Notification> getByRecipient(Integer recipientId) {
        return repository.findByRecipientIdOrRecipientIdIsNullOrderBySentAtDesc(recipientId);
    }

    @Override
    public long getUnreadCount(Integer recipientId) {
        long personalUnread = repository.countByRecipientIdAndIsRead(recipientId, false);
        long globalUnread = repository.countByRecipientIdIsNullAndIsRead(false);
        return personalUnread + globalUnread;
    }

    @Override
    @Transactional
    public void deleteNotification(Long notificationId) {
        repository.deleteById(notificationId);
    }

    @Async
    public void sendBroadcastEmails(Notification notification) {
        try {
            log.info("📢 Fetching all user emails for broadcast...");
            List<String> emails = authClient.getAllUserEmails();
            log.info("📢 Sending broadcast email to {} users", emails.size());
            
            for (String email : emails) {
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(email);
                mail.setSubject(notification.getTitle());
                mail.setText(notification.getMessage());
                mailSender.send(mail);
            }
            log.info("✅ Broadcast emails sent successfully!");
        } catch (Exception e) {
            log.error("❌ Failed to send broadcast emails: {}", e.getMessage());
        }
    }

    @Override
    @Async
    public void sendEmail(Notification notification) {
        try {
            log.info("📧 Sending actual email: Title='{}' to User ID='{}'", notification.getTitle(), notification.getRecipientId());
            
            // Fetch real user email from AUTH-SERVICE
            UserResponse user = authClient.getUserById(notification.getRecipientId());
            if (user == null || user.getEmail() == null) {
                log.error("❌ Cannot send email: User not found or email is null for ID: {}", notification.getRecipientId());
                return;
            }

            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(user.getEmail()); 
            mail.setSubject(notification.getTitle());
            mail.setText(notification.getMessage());
            
            mailSender.send(mail);
            log.info("✅ Email sent successfully to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("❌ Failed to send email: {}", e.getMessage());
        }
    }

    @Override
    public List<Notification> getAll() {
        return repository.findAll();
    }
}
