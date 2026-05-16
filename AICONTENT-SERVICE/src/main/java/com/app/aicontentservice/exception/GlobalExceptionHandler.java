package com.app.aicontentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuotaExceededException.class)
    public ResponseEntity<Map<String, String>> handleQuotaExceeded(QuotaExceededException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "QUOTA_EXCEEDED");
        error.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Map<String, String>> handleNumberFormat(NumberFormatException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "INVALID_ID");
        error.put("message", "User ID must be a valid number");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "AI_SERVICE_ERROR");
        error.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
