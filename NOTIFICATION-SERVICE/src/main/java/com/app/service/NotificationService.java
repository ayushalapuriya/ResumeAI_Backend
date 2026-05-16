package com.app.service;

import com.app.entity.Notification;
import java.util.List;

public interface NotificationService {
    Notification send(Notification notification);
    void sendBulk(List<Notification> notifications);
    void markAsRead(Long notificationId);
    void markAllRead(Integer recipientId);
    List<Notification> getByRecipient(Integer recipientId);
    long getUnreadCount(Integer recipientId);
    void deleteNotification(Long notificationId);
    void sendEmail(Notification notification);
    List<Notification> getAll();
}
