package com.jobsTrackerApp.Repository;

import com.jobsTrackerApp.Model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByJobApplicationIdAndJobApplicationUserIdOrderByCreatedAtDesc(Long jobApplicationId, Long userId);

    Optional<Note> findByIdAndJobApplicationId(Long id, Long jobApplicationId);

    long countByJobApplicationId(Long jobApplicationId);
}
