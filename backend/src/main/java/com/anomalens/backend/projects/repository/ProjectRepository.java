package com.anomalens.backend.projects.repository;

import com.anomalens.backend.projects.entity.Project;
import com.anomalens.backend.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByNameAndOwner(String name, User owner);
    List<Project> findByOwner(User owner);
    Optional<Project> findByIdAndOwner(Long id, User owner);
    Optional<Project> findByNameAndOwner(String name, User owner);
}
