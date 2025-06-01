package com.jobsTrackerApp.DTO;

import com.jobsTrackerApp.Model.Reminder;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReminderDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "title is required")
        private String title;

        private String description;

        @NotNull(message = "reminder date is required")
        @Future(message = "reminder date must be in the future")
        private LocalDateTime reminderDate;

        private Long jobApplicationId;
        private Reminder.ReminderPriority priority;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime reminderDate;
        private boolean completed;
        private Reminder.ReminderPriority priority;
        private JobApplicationDto.Response jobApplication;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompletionRequest {
        private boolean completed;
    }

}
