package com.jobsTrackerApp.DTO;

import com.jobsTrackerApp.Model.ApplicationStage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ApplicationStageDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        @NotBlank(message = "stage name is required")
        private String stageName;

        @NotNull(message = "status is required")
        private ApplicationStage.StageStatus status;

        private LocalDateTime stageDate;
        private String location;
        private String notes;
        private String contactPerson;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String stageName;
        private ApplicationStage.StageStatus status;
        private LocalDateTime stageDate;
        private String location;
        private String notes;
        private String contactPerson;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatusUpdateRequest {
        private ApplicationStage.StageStatus status;
    }

}
