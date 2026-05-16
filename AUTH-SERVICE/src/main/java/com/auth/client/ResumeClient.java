package com.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "RESUME-SERVICE")
public interface ResumeClient {
    @GetMapping("/resumes/count")
    long getResumeCount();
}
