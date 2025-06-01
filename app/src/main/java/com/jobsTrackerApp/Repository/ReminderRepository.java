package com.jobsTrackerApp.Repository;

import com.jobsTrackerApp.Model.JobApplication;
import com.jobsTrackerApp.Model.Reminder;
import com.jobsTrackerApp.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Page<Reminder> findByUser(User user, Pageable pageable);

    Page<Reminder> findByUserAndJobApplication(User user, JobApplication jobApplication, Pageable pageable);

    List<Reminder> findByUserAndCompletedFalseAndReminderDateBefore(User user, LocalDateTime dateTime);

    @Query("SELECT r FROM Reminder r WHERE r.user = :user AND r.completed = :completed " +
            "ORDER BY r.reminderDate ASC")
    Page<Reminder> findByUserAndCompleted(@Param("user") User user, @Param("completed") boolean completed, Pageable pageable);

    @Query("SELECT r FROM Reminder r WHERE r.user = :user AND r.completed = false " +
            "AND r.reminderDate BETWEEN :startDate AND :endDate ORDER BY r.reminderDate ASC")
    List<Reminder> findUpcomingReminders(
            @Param("user") User user,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
