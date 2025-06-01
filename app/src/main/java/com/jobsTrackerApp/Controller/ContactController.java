package com.jobsTrackerApp.Controller;

import com.jobsTrackerApp.DTO.ContactDto;
import com.jobsTrackerApp.Service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/job-applications/{jobApplicationId}/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactDto.Response> createContact(
            @PathVariable Long userId,
            @PathVariable Long jobApplicationId,
            @Valid @RequestBody ContactDto.Request request) {
        return ResponseEntity.ok(contactService.createContact(userId, jobApplicationId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto.Response> getContactById(
            @PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @GetMapping
    public ResponseEntity<List<ContactDto.Response>> getAllContactsByJobApplication(
            @PathVariable Long jobApplicationId) {
        return ResponseEntity.ok(contactService.getAllContactsByJobApplication(jobApplicationId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDto.Response> updateContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactDto.Request request) {
        return ResponseEntity.ok(contactService.updateContact(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(
            @PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}