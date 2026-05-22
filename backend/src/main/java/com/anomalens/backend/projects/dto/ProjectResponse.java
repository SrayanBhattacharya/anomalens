package com.anomalens.backend.projects.dto;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        Long ownerId,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
) {}
