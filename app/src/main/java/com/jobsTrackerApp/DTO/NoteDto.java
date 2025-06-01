package com.jobsTrackerApp.DTO;

import com.jobsTrackerApp.Model.Note;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class NoteDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "title is required")
        private String title;

        @NotBlank(message = "content is required")
        private String content;

        @NotNull(message = "note type is required")
        private Note.NoteType type;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private Note.NoteType type;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
