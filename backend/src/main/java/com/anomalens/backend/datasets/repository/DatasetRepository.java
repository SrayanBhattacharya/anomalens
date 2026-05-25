package com.anomalens.backend.datasets.repository;

import com.anomalens.backend.datasets.entity.Dataset;
import com.anomalens.backend.projects.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DatasetRepository extends JpaRepository<Dataset, Long> {
    boolean existsByNameAndProject(String name, Project project);
    List<Dataset> findByProject(Project project);
    Optional<Dataset> findByIdAndProject(Long id, Project project);
    Optional<Dataset> findByNameAndProject(String name, Project project);
}
