package com.jobsTrackerApp.Repository;

import com.jobsTrackerApp.Model.JobApplication;
import com.jobsTrackerApp.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Page<JobApplication> findByUser(User user, Pageable pageable);

    Page<JobApplication> findByUserAndStatus(User user, JobApplication.JobApplicationStatus status, Pageable pageable);

    @Query("SELECT j FROM JobApplication j WHERE j.user = :user AND" +
            "(:companyName IS NULL OR LOWER(j.companyName) LIKE LOWER(CONCAT('%', :companyName, '%'))) AND" +
            "(:jobTitle IS NULL OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :jobTitle, '%'))) AND" +
            "(:status IS NULL OR j.status = :status)")

    Page<JobApplication> searchJobApplications(
            @Param("user") User user,
            @Param("companyName") String companyName,
            @Param("jobTitle") String jobTitle,
            @Param("status") JobApplication.JobApplicationStatus status,
            Pageable pageable);

    @Query("SELECT COUNT(j) FROM JobApplication j WHERE j.user = :user AND j.status = :status")
    long countByUserAndStatus(@Param("user") User user, @Param("status") JobApplication.JobApplicationStatus status);

    @Query("SELECT COUNT(j) FROM JobApplication j WHERE j.user = :user AND j.applicationDate BETWEEN :startDate AND :endDate")
    long countByUserAndApplicationDateBetween(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
