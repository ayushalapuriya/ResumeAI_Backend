package com.app.repository;

import com.app.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByRecipientIdOrderBySentAtDesc(Integer recipientId);
    
    List<Notification> findByRecipientIdOrRecipientIdIsNullOrderBySentAtDesc(Integer recipientId);
    
    List<Notification> findByRecipientIdAndIsReadOrderBySentAtDesc(Integer recipientId, boolean isRead);
    
    long countByRecipientIdAndIsRead(Integer recipientId, boolean isRead);
    
    long countByRecipientIdIsNullAndIsRead(boolean isRead);

    List<Notification> findByRecipientIdIsNullOrderBySentAtDesc();
    
    List<Notification> findByType(String type);
    
    List<Notification> findByRelatedId(String relatedId);
    
    void deleteByNotificationId(Long notificationId);
}
