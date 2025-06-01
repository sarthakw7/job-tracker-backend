package com.jobsTrackerApp.Repository;

import com.jobsTrackerApp.Model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // Find all contacts for a job application
    List<Contact> findByJobApplicationId(Long jobApplicationId);

    // Find a contact by id and job application id
    Optional<Contact> findByIdAndJobApplicationId(Long id, Long jobApplicationId);

    // Count contacts for a user (via job application)
    long countByJobApplicationUserId(Long userId);
}
