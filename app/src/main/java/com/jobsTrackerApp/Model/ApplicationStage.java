package com.jobsTrackerApp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_stages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_application_id", nullable = false)
    private JobApplication jobApplication;

    @Column(nullable = false)
    private String stageName;

    @Enumerated(EnumType.STRING)
    private StageStatus status;

    private LocalDateTime stageDate;
    private String location;
    private String notes;
    private String contactPerson;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum StageStatus{
        PENDING,
        COMPLETED,
        FAILED,
        UPCOMING,
        CANCELLED
    }

}
