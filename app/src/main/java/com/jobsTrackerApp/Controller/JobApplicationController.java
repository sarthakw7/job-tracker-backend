package com.jobsTrackerApp.Controller;

import com.jobsTrackerApp.DTO.JobApplicationDto;
import com.jobsTrackerApp.Service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{usersId}/job-applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<JobApplicationDto.Response> createJobApplication(
            @PathVariable Long userId,
            @Valid @RequestBody JobApplicationDto.Request request
    ) {
        return ResponseEntity.ok(jobApplicationService.createJobApplication(userId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationDto.Response> getJobApplicationById(
            @PathVariable Long userId,
            @PathVariable Long  id
    ) {
        return ResponseEntity.ok(jobApplicationService.getJobApplicationById(userId, id));
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationDto.Response>> getAllJobApplications(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(jobApplicationService.getAllJobApplications(userId, page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationDto.Response> updateJobApplication(
            @PathVariable Long userId,
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationDto.Request request
    ) {
        return ResponseEntity.ok(jobApplicationService.updateJobApplication(userId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobApplication(
            @PathVariable Long userId,
            @PathVariable Long id
    ) {
        jobApplicationService.deleteJobApplication(userId, id);
        return ResponseEntity.noContent().build();
    }
}
