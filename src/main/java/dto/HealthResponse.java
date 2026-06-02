package br.com.argus.ia.dto;

import java.time.LocalDateTime;

public class HealthResponse {

    private String status;
    private String service;
    private String message;
    private LocalDateTime timestamp;

    public HealthResponse() {
    }

    public HealthResponse(String status, String service, String message, LocalDateTime timestamp) {
        this.status = status;
        this.service = service;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}