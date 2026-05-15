package com.app.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private Integer recipientId; // Can be null for bulk/broadcast

    private String type; // ATS_COMPLETE, EXPORT_READY, AI_DONE, JOB_MATCH, PLAN_CHANGE, QUOTA_WARNING, BROADCAST

    private String title;

    @Column(nullable = false, length = 2000)
    private String message;

    private String channel; // APP, EMAIL, BOTH

    private String relatedId; // resumeId, exportJobId, etc.

    private String relatedType;

    @Builder.Default
    private boolean isRead = false;

    private LocalDateTime sentAt;

    @PrePersist
    public void prePersist() {
        this.sentAt = LocalDateTime.now();
    }
}
