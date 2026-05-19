package org.app.aicontentservice.controllers;

import lombok.RequiredArgsConstructor;
import org.app.aicontentservice.service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiResource {

    private final AiService aiService;

    @PostMapping("/summary")
    public ResponseEntity<?> summary(@RequestBody Map<String, Object> req) {
        int userId = 0;
        Object userIdObj = req.get("userId");
        if (userIdObj instanceof Number) {
            userId = ((Number) userIdObj).intValue();
        } else if (userIdObj instanceof String) {
            userId = Integer.parseInt((String) userIdObj);
        }

        return ResponseEntity.ok(
                Map.of("summary", aiService.generateSummary(
                        userId,
                        (String) req.get("resume"),
                        (String) req.get("jobDesc")))
        );
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<?> history(@PathVariable int userId) {
        return ResponseEntity.ok(aiService.getAiHistory(userId));
    }

    @GetMapping("/quota/{userId}")
    public ResponseEntity<?> quota(@PathVariable int userId) {
        return ResponseEntity.ok(aiService.getDetailedQuota(userId));
    }
}