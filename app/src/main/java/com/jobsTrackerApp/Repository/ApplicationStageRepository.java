package com.jobsTrackerApp.Repository;

import com.jobsTrackerApp.Model.ApplicationStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationStageRepository extends JpaRepository<ApplicationStage, Long> {
    List<ApplicationStage> findByJobApplicationIdOrderByStageDateAsc(Long jobApplicationId);

    Optional<ApplicationStage> findByIdAndJobApplicationUserId(Long id, Long userId);

    List<ApplicationStage> findByJobApplicationUserIdOrderByCreatedAtDesc(Long userId);

    long countByJobApplicationId(Long jobApplicationId);
}
