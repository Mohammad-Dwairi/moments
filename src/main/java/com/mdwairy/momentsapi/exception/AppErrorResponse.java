package com.mdwairy.momentsapi.exception;

import lombok.Data;

@Data
public class AppErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}
