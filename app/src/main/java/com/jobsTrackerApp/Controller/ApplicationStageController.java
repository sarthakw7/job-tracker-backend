package com.jobsTrackerApp.Controller;

import com.jobsTrackerApp.DTO.ApplicationStageDto;
import com.jobsTrackerApp.Service.ApplicationStageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/job-applications/{jobApplicationId}/stages")
@RequiredArgsConstructor
public class ApplicationStageController {
    private final ApplicationStageService applicationStageService;

    @PostMapping
    public ResponseEntity<ApplicationStageDto.Response> createStage(
            @PathVariable Long userId,
            @PathVariable Long jobApplicationId,
            @Valid @RequestBody ApplicationStageDto.Request request) {
        return ResponseEntity.ok(applicationStageService.createStage(userId, jobApplicationId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationStageDto.Response> getStageById(
            @PathVariable Long userId,
            @PathVariable Long id) {
        return ResponseEntity.ok(applicationStageService.getStageById(userId, id));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationStageDto.Response>> getAllStagesByJobApplication(
            @PathVariable Long userId,
            @PathVariable Long jobApplicationId) {
        return ResponseEntity.ok(applicationStageService.getAllStagesByJobApplication(userId, jobApplicationId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationStageDto.Response> updateStage(
            @PathVariable Long userId,
            @PathVariable Long id,
            @Valid @RequestBody ApplicationStageDto.Request request) {
        return ResponseEntity.ok(applicationStageService.updateStage(userId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(
            @PathVariable Long userId,
            @PathVariable Long id) {
        applicationStageService.deleteStage(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationStageDto.Response> updateStageStatus(
            @PathVariable Long userId,
            @PathVariable Long id,
            @Valid @RequestBody ApplicationStageDto.StatusUpdateRequest request) {
        return ResponseEntity.ok(applicationStageService.updateStageStatus(userId, id, request));
    }
}