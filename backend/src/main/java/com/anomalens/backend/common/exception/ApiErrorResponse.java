package com.anomalens.backend.common.exception;

public record ApiErrorResponse(
        String message,
        int status
) {}
