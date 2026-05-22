package com.anomalens.backend.projects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest(
        @NotBlank
        @Size(min = 1, max = 100)
        String name,

        String description
) {}
