package com.jobsTrackerApp.Controller;

import com.jobsTrackerApp.DTO.ReminderDto;
import com.jobsTrackerApp.Service.ReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping
    public ResponseEntity<ReminderDto.Response> createReminder(
            @PathVariable Long userId,
            @Valid @RequestBody ReminderDto.Request request
    ) {
        return ResponseEntity.ok(reminderService.createReminder(userId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderDto.Response> getReminderById(
            @PathVariable Long userID,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(reminderService.getReminderById(userID, id));
    }

    @GetMapping
    public ResponseEntity<List<ReminderDto.Response>> getAllReminders(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(reminderService.getAllReminders(userId, page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderDto.Response> updateReminder (
            @PathVariable Long userId,
            @PathVariable Long id,
            @Valid @RequestBody ReminderDto.Request request
    ) {
        return ResponseEntity.ok(reminderService.updateReminder(userId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder (
            @PathVariable Long userId,
            @PathVariable Long id
    ) {
        reminderService.deleteReminder(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ReminderDto.Response> markAsCompleted(
            @PathVariable Long userId,
            @PathVariable Long id
    ) {
        ReminderDto.Response response = reminderService.markAsCompleted(id, userId) != null ?
                new org.modelmapper.ModelMapper().map(reminderService.markAsCompleted(id, userId), ReminderDto.Response.class) : null;

        return ResponseEntity.ok(response);
    }

}





























