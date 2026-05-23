package com.anomalens.backend.projects.service;

import com.anomalens.backend.common.exception.ProjectAlreadyExistsException;
import com.anomalens.backend.common.exception.ProjectNotFoundException;
import com.anomalens.backend.projects.dto.CreateProjectRequest;
import com.anomalens.backend.projects.dto.ProjectResponse;
import com.anomalens.backend.projects.entity.Project;
import com.anomalens.backend.projects.repository.ProjectRepository;
import com.anomalens.backend.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    private ProjectResponse mapToResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getOwner().getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

    public ProjectResponse createProject(User owner, CreateProjectRequest request) {
        if (projectRepository.existsByNameAndOwner(request.name(), owner)) {
            throw new ProjectAlreadyExistsException("Project with name '" + request.name() + "' already exists for this user");
        }

        Project project = Project.builder()
                .name(request.name())
                .description(request.description())
                .owner(owner)
                .build();

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }

    public List<ProjectResponse> getProjects(User owner) {
        List<Project> projects = projectRepository.findByOwner(owner);

        return projects.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProjectResponse getProject(User owner, Long projectId) {
        Project project = projectRepository.findByIdAndOwner(projectId, owner)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id '" + projectId + "' not found for this user"));

        return mapToResponse(project);
    }
}
