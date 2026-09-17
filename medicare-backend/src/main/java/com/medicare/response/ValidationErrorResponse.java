package com.medicare.response;


import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;

    private Map<String, String> errors;

    public ValidationErrorResponse(
            LocalDateTime timestamp,
            int status,
            String message,
            String path,
            Map<String, String> errors) {

        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}