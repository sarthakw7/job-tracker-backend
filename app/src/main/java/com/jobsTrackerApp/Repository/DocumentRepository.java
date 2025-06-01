package com.jobsTrackerApp.Repository;

import com.jobsTrackerApp.Model.Document;
import com.jobsTrackerApp.Model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByJobApplicationId(Long jobApplicationId);

    List<Document> findByJobApplicationAndDocumentType(JobApplication jobApplication, Document.DocumentType documentType);

    @Query("SELECT d FROM Document d JOIN d.jobApplication ja WHERE ja.user.id = :userId")
    List<Document> findAllDocumentsByUserId(@Param("userId") Long userId);

    boolean existsByJobApplicationIdAndNameAndType(Long jobApplicationId, String name, String type);
}
