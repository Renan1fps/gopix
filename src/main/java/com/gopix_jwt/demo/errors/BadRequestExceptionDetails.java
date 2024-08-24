package com.gopix_jwt.demo.errors;

import java.time.LocalDateTime;

public class BadRequestExceptionDetails {

    private String message;
    private Integer statusCode;
    private LocalDateTime timestamp;

    public BadRequestExceptionDetails() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}