package dev.joriang.tpspringboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private long timestamp;

    public ApiResponse(T data) {
        this.success = true;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public ApiResponse(String errorMessage) {
        this.success = false;
        this.message = errorMessage;
        this.timestamp = System.currentTimeMillis();
    }
} 