package org.app.notificationservice.client;

import org.app.notificationservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthClient {
    
    @GetMapping("/admin/users/emails")
    List<String> getAllUserEmails();

    @GetMapping("/auth/{id}")
    UserResponse getUserById(@PathVariable("id") Integer id);
}
