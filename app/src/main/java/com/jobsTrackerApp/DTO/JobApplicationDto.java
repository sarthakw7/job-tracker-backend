package com.jobsTrackerApp.DTO;

import com.jobsTrackerApp.Model.JobApplication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class JobApplicationDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "company name is required")
        private String companyName;

        @NotBlank(message = "job title is required")
        private String jobTitle;

        private String jobDescription;
        private String jobLocation;
        private String jobUrl;
        private BigDecimal salary;

        @PastOrPresent(message = "application date cannot be in the future")
        private LocalDate applicationDate;

        private JobApplication.JobApplicationStatus status;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String jobTitle;
        private String jobDescription;
        private String jobLocation;
        private String jobUrl;
        private BigDecimal salary;
        private LocalDate applicationDate;
        protected JobApplication.JobApplicationStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailedResponse {
        private Long id;
        private String jobTitle;
        private String jobDescription;
        private String jobLocation;
        private String jobUrl;
        private BigDecimal salary;
        private LocalDate applicationDate;
        protected JobApplication.JobApplicationStatus status;
        private List<ApplicationStageDto.Response> stages;
        private List<ContactDto.Response> contacts;
        private List<DocumentDto.Response> documents;
        private List<NoteDto.Response> notes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatusUpdateRequest {
        private JobApplication.JobApplicationStatus status;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchRequest {
        private String companyName;
        private String jobTitle;
        private JobApplication.JobApplicationStatus status;
        private int page;
        private int size;
        private String sortBy;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatisticsResponse {
        private long savedCount;
        private long appliedCount;
        private long interviewingCount;
        private long offeredCount;
        private long acceptedCount;
        private long rejectedCount;
        private long withdrawnCount;
        private long totalCount;
        private long lastWeekApplications;
        private long lastMonthApplications;
    }
}
