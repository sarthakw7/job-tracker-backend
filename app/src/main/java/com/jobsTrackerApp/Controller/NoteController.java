package com.jobsTrackerApp.Controller;

import com.jobsTrackerApp.DTO.NoteDto;
import com.jobsTrackerApp.Service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/job-applications/{jobApplicationId}/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDto.Response> createNote(
            @PathVariable Long userId,
            @PathVariable Long jobApplicationId,
            @Valid @RequestBody NoteDto.Request request) {
        return ResponseEntity.ok(noteService.createNote(userId, jobApplicationId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto.Response> getNoteById(
            @PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @GetMapping
    public ResponseEntity<List<NoteDto.Response>> getAllNotesByJobApplication(
            @PathVariable Long userId,
            @PathVariable Long jobApplicationId) {
        return ResponseEntity.ok(noteService.getAllNotesByJobApplication(userId, jobApplicationId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto.Response> updateNote(
            @PathVariable Long id,
            @Valid @RequestBody NoteDto.Request request) {
        return ResponseEntity.ok(noteService.updateNote(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}