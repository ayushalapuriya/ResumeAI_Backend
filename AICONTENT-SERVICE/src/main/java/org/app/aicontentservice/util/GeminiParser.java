package org.app.aicontentservice.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeminiParser {

    public static String getText(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonNode candidates = root.path("candidates");
            if (candidates.isMissingNode() || !candidates.isArray() || candidates.isEmpty()) {
                // Check for error messages in the response
                JsonNode error = root.path("error");
                if (!error.isMissingNode()) {
                    throw new RuntimeException("Gemini API Error: " + error.path("message").asText());
                }
                throw new RuntimeException("Invalid AI response: No candidates found");
            }

            return candidates.get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
            if (e instanceof RuntimeException) throw (RuntimeException) e;
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage());
        }
    }

    public static int getTokens(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            return root.path("usageMetadata")
                    .path("totalTokenCount")
                    .asInt();
        } catch (Exception e) {
            return 0;
        }
    }
}