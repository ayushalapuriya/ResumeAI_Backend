package com.app.entity;

import com.app.enums.SectionType;
import com.app.util.JsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "resume_sections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ResumeSection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sectionId;

    @Column(nullable = false)
    private Integer resumeId;

    @Enumerated(EnumType.STRING)
    private SectionType sectionType;

    private String title;

    // Flexible JSON content stored as String in MySQL
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> content;

    private Integer displayOrder;

    private Boolean isVisible;

    private Boolean aiGenerated;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}