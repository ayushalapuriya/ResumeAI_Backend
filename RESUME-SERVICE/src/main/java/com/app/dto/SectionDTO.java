package com.app.dto;

import com.app.enums.SectionType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionDTO {
    private String sectionId;
    private Integer resumeId;
    private SectionType sectionType;
    private String title;
    private Map<String, Object> content;
    private Integer displayOrder;
    private Boolean isVisible;
    private Boolean aiGenerated;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
