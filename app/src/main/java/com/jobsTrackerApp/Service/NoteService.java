package com.jobsTrackerApp.Service;

import com.jobsTrackerApp.DTO.NoteDto;
import com.jobsTrackerApp.Exception.ResourceNotFoundException;
import com.jobsTrackerApp.Model.JobApplication;
import com.jobsTrackerApp.Model.Note;
import com.jobsTrackerApp.Repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {
    private final NoteRepository noteRepository;
    private final JobApplicationService jobApplicationService;
    private final ModelMapper modelMapper;

    @Transactional
    public NoteDto.Response createNote(Long userId, Long jobApplicationId, NoteDto.Request request) {
        JobApplication jobApplication = modelMapper.map(jobApplicationService.getJobApplicationById(userId, jobApplicationId), JobApplication.class);
        Note note = modelMapper.map(request, Note.class);
        note.setJobApplication(jobApplication);
        Note saved = noteRepository.save(note);
        return modelMapper.map(saved, NoteDto.Response.class);
    }

    @Transactional(readOnly = true)
    public NoteDto.Response getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        return modelMapper.map(note, NoteDto.Response.class);
    }

    @Transactional(readOnly = true)
    public List<NoteDto.Response> getAllNotesByJobApplication(Long userId, Long jobApplicationId) {
        List<Note> notes = noteRepository.findByJobApplicationIdAndJobApplicationUserIdOrderByCreatedAtDesc(jobApplicationId, userId);
        return notes.stream()
                .map(note -> modelMapper.map(note, NoteDto.Response.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public NoteDto.Response updateNote(Long id, NoteDto.Request request) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        modelMapper.map(request, note);
        Note updated = noteRepository.save(note);
        return modelMapper.map(updated, NoteDto.Response.class);
    }

    @Transactional
    public void deleteNote(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        noteRepository.delete(note);
    }
}