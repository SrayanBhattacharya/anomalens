package com.anomalens.backend.projects.controller;

import com.anomalens.backend.auth.security.CustomUserDetails;
import com.anomalens.backend.projects.dto.CreateProjectRequest;
import com.anomalens.backend.projects.dto.ProjectResponse;
import com.anomalens.backend.projects.service.ProjectService;
import com.anomalens.backend.users.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> create(
            @Valid @RequestBody CreateProjectRequest request,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User owner = userDetails.getUser();

        ProjectResponse response = projectService.createProject(owner, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
