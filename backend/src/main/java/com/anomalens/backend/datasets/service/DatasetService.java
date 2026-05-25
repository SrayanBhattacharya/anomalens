package com.anomalens.backend.datasets.service;

import com.anomalens.backend.common.exception.DatasetNotFoundException;
import com.anomalens.backend.common.exception.ProjectNotFoundException;
import com.anomalens.backend.datasets.dto.DatasetResponse;
import com.anomalens.backend.datasets.entity.Dataset;
import com.anomalens.backend.datasets.repository.DatasetRepository;
import com.anomalens.backend.projects.entity.Project;
import com.anomalens.backend.projects.repository.ProjectRepository;
import com.anomalens.backend.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatasetService {
    private final DatasetRepository datasetRepository;
    private final ProjectRepository projectRepository;

    private DatasetResponse mapToResponse(Dataset dataset) {
        return new DatasetResponse(
                dataset.getId(),
                dataset.getProject().getId(),
                dataset.getName(),
                dataset.getOriginalFilename(),
                dataset.getFileSize(),
                dataset.getContentType(),
                dataset.getLogFormat(),
                dataset.getUploadedAt()
        );
    }

    private Project getOwnedProject(User owner, Long projectId) {
        return projectRepository.findByIdAndOwner(projectId, owner)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id '" + projectId + "' not found for this user"));
    }

    private Dataset getProjectDataset(Project project, Long datasetId) {
        return datasetRepository.findByIdAndProject(datasetId, project)
                .orElseThrow(() -> new DatasetNotFoundException("Dataset with id '" + datasetId + "' not found in this project"));
    }

    @Transactional(readOnly = true)
    public List<DatasetResponse> getDatasets(User owner, Long projectId) {
        Project project = getOwnedProject(owner, projectId);
        List<Dataset> datasets = datasetRepository.findByProject(project);

        return datasets.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public DatasetResponse getDataset(User owner, Long projectId, Long datasetId) {
        Project project = getOwnedProject(owner, projectId);
        Dataset dataset = getProjectDataset(project, datasetId);

        return mapToResponse(dataset);
    }
}
