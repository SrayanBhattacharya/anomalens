package com.anomalens.backend.datasets.dto;

import com.anomalens.backend.datasets.entity.LogFormat;

import java.time.Instant;

public record DatasetResponse(
        Long id,
        Long projectId,
        String name,
        String originalFilename,
        Long fileSize,
        String contentType,
        LogFormat logFormat,
        Instant uploadedAt
) {}
