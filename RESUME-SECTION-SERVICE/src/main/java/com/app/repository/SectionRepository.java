package com.app.repository;

import com.app.entity.ResumeSection;
import com.app.enums.SectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<ResumeSection, String> {

    List<ResumeSection> findByResumeId(Integer resumeId);

    Optional<ResumeSection> findByResumeIdAndSectionType(Integer resumeId, SectionType sectionType);

    List<ResumeSection> findByResumeIdOrderByDisplayOrder(Integer resumeId);

    List<ResumeSection> findByAiGenerated(Boolean aiGenerated);

    long countByResumeId(Integer resumeId);

    @Transactional
    void deleteByResumeId(Integer resumeId);
}