package com.app.client;

import com.app.dto.SectionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "RESUME-SECTION-SERVICE", path = "/sections")
public interface SectionClient {

    @PostMapping
    SectionDTO add(@RequestBody SectionDTO section);

    @GetMapping("/resume/{resumeId}")
    List<SectionDTO> getByResume(@PathVariable Integer resumeId);

    @GetMapping("/{id}")
    SectionDTO getById(@PathVariable String id);

    @PutMapping("/{id}")
    SectionDTO update(@PathVariable String id, @RequestBody SectionDTO section);

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id);

    @DeleteMapping("/resume/{resumeId}")
    void deleteAllByResume(@PathVariable Integer resumeId);
}
