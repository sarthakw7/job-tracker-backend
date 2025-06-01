package com.jobsTrackerApp.DTO;

import com.jobsTrackerApp.Model.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class DocumentDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "Name is required")
        private String name;

        @NotNull(message = "Document type is required")
        private Document.DocumentType documentType;

        private String version;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private String type;
        private Document.DocumentType documentType;
        private String version;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
