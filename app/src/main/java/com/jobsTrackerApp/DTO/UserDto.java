package com.jobsTrackerApp.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

public class UserDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        @NotBlank(message = "Email is required")
        @Email(message = "invalid email format")
        private String email;

        @NotBlank(message = "password is requires")
        @Size(min = 8, message = "password must be at least 8 characters")
        private String password;

        @NotBlank(message = "Full name is required")
        private String fullName;

        private String Location;
        private String bio;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private Long id;
        private String email;
        private String fullName;
        private String location;
        private String profilePicture;
        private String bio;
        private Set<String> roles;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest{
        private String fullName;
        private String location;
        private String bio;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PasswordChangeRequest {
        @NotBlank(message = "current password is required")
        private String currentPassword;

        @NotBlank(message = "new password is required")
        @Size(min = 8, message = "new password must be at least 8 characters")
        private String newPassword;

        @NotBlank(message = "confirm password is required")
        private String confirmPassword;
    }
}
