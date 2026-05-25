package com.anomalens.backend.common.exception;

public class DatasetNotFoundException extends RuntimeException {
    public DatasetNotFoundException(String message) {
        super(message);
    }
}
