package com.app.service;

import com.app.entity.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "notification_queue")
    public void receiveNotification(Map<String, Object> message) {
        log.info("Received notification event via RabbitMQ: {}", message);

        try {
            Notification notification = new Notification();
            
            if (message.containsKey("recipientId") && message.get("recipientId") != null) {
                notification.setRecipientId(Integer.valueOf(message.get("recipientId").toString()));
            }
            notification.setTitle((String) message.get("title"));
            notification.setMessage((String) message.get("message"));
            notification.setType((String) message.get("type"));
            notification.setChannel((String) message.get("channel"));

            notificationService.send(notification);
            log.info("Successfully processed RabbitMQ notification event");
        } catch (Exception e) {
            log.error("Failed to process RabbitMQ notification event", e);
        }
    }
}
