package com.anomalens.backend.datasets.entity;

import com.anomalens.backend.projects.entity.Project;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(
        name = "datasets",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "project_id"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dataset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String originalFilename;

    @Column(nullable = false, length = 255)
    private String storedFilename;

    @Column(nullable = false, length = 500)
    private String filePath;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false, length = 100)
    private String contentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogFormat logFormat;

    @Column(nullable = false, updatable = false)
    private Instant uploadedAt;

    @PrePersist
    protected void onUpload() {
        this.uploadedAt = Instant.now();
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id")
    private Project project;
}
