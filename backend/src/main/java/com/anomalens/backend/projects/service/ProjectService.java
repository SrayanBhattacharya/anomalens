package com.anomalens.backend.projects.service;

import com.anomalens.backend.common.exception.ProjectAlreadyExistsException;
import com.anomalens.backend.projects.dto.CreateProjectRequest;
import com.anomalens.backend.projects.dto.ProjectResponse;
import com.anomalens.backend.projects.entity.Project;
import com.anomalens.backend.projects.repository.ProjectRepository;
import com.anomalens.backend.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

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

        return new ProjectResponse(
                savedProject.getId(),
                savedProject.getOwner().getId(),
                savedProject.getName(),
                savedProject.getDescription(),
                savedProject.getCreatedAt(),
                savedProject.getUpdatedAt()
        );
    }
}
