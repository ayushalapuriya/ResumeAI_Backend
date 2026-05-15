package com.app;

import com.app.entity.Notification;
import com.app.repository.NotificationRepository;
import com.app.service.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private NotificationServiceImpl service;

    @Test
    public void testGetByRecipientIncludesBroadcasts() {
        Integer userId = 1;
        Notification personal = Notification.builder()
                .notificationId(1L)
                .recipientId(userId)
                .message("Personal")
                .sentAt(LocalDateTime.now())
                .build();
        
        Notification broadcast = Notification.builder()
                .notificationId(2L)
                .recipientId(null)
                .message("Global")
                .sentAt(LocalDateTime.now().minusHours(1))
                .build();

        when(repository.findByRecipientIdOrderBySentAtDesc(userId)).thenReturn(Arrays.asList(personal));
        when(repository.findByRecipientIdIsNullOrderBySentAtDesc()).thenReturn(Arrays.asList(broadcast));

        List<Notification> result = service.getByRecipient(userId);

        assertEquals(2, result.size());
        assertEquals("Personal", result.get(0).getMessage());
        assertEquals("Global", result.get(1).getMessage());
    }
}
