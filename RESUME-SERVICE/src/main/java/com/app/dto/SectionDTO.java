package com.app.dto;

import lombok.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionDTO {
    private String sectionId;
    private Integer resumeId;
    private String sectionType;
    private String title;
    private Map<String, Object> content;
    private Integer displayOrder;
    private Boolean isVisible;
    private Boolean aiGenerated;
}
