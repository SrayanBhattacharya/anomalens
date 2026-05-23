package com.anomalens.backend.projects.controller;

import com.anomalens.backend.auth.security.CustomUserDetails;
import com.anomalens.backend.projects.dto.CreateProjectRequest;
import com.anomalens.backend.projects.dto.ProjectResponse;
import com.anomalens.backend.projects.dto.UpdateProjectRequest;
import com.anomalens.backend.projects.service.ProjectService;
import com.anomalens.backend.users.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    private User getOwner(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> create(
            @Valid @RequestBody CreateProjectRequest request,
            Authentication authentication
    ) {
        User owner = getOwner(authentication);

        ProjectResponse response = projectService.createProject(owner, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects(Authentication authentication) {
        User owner = getOwner(authentication);

        List<ProjectResponse> projects = projectService.getProjects(owner);
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(
            Authentication authentication,
            @PathVariable Long id
    ) {
        User owner = getOwner(authentication);

        ProjectResponse project = projectService.getProject(owner, id);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @Valid @RequestBody UpdateProjectRequest request,
            Authentication authentication,
            @PathVariable Long id
    ) {
        User owner = getOwner(authentication);

        ProjectResponse project = projectService.updateProject(owner, id, request);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            Authentication authentication,
            @PathVariable Long id
    ) {
        User owner = getOwner(authentication);

        projectService.deleteProject(owner, id);
        return ResponseEntity.noContent().build();
    }
}
