package com.mdwairy.momentsapi.exception;

public class InvalidRequestParamValue extends RuntimeException {
    public InvalidRequestParamValue(String message) {
        super(message);
    }
}
