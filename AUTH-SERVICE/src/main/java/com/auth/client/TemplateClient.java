package com.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "TEMPLATE-SERVICE")
public interface TemplateClient {
    @GetMapping("/templates/count")
    long getTemplateCount();
}
