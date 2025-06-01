package com.jobsTrackerApp.Controller;

import com.jobsTrackerApp.DTO.DocumentDto;
import com.jobsTrackerApp.Service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{usersId}/job-applications/{jobApplicationId}/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<DocumentDto.Response> createDocument(
            @PathVariable Long userId,
            @PathVariable Long jobApplicationId,
            @Valid @RequestBody DocumentDto.Request request
    ) {
        return ResponseEntity.ok(documentService.createDocument(userId, jobApplicationId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto.Response> getDocumentById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto.Response>> getAllDocumentsByJobApplication(
            @PathVariable Long jobApplicationId
    ) {
        return ResponseEntity.ok(documentService.getAllDocumentsByJobApplication(jobApplicationId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentDto.Response> updateDocument(
            @PathVariable Long id,
            @Valid @RequestBody DocumentDto.Request request
    ) {
        return ResponseEntity.ok(documentService.updateDocument(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable Long id
    ) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}















