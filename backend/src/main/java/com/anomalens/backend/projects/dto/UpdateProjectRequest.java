package com.anomalens.backend.projects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProjectRequest(
        @NotBlank
        @Size(min = 3, max = 100)
        String name,

        String description
) {}
