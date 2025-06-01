package com.jobsTrackerApp.Service;

import com.jobsTrackerApp.DTO.ReminderDto;
import com.jobsTrackerApp.Exception.ResourceNotFoundException;
import com.jobsTrackerApp.Model.JobApplication;
import com.jobsTrackerApp.Model.Reminder;
import com.jobsTrackerApp.Model.User;
import com.jobsTrackerApp.Repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserService userService;
    private final JobApplicationService jobApplicationService;
    private final ModelMapper modelMapper;

    @Transactional
    public ReminderDto.Response createReminder(Long userId, ReminderDto.Request request) {
        User user = userService.getUserById(userId);
        Reminder reminder = modelMapper.map(request, Reminder.class);
        reminder.setUser(user);
        if (request.getJobApplicationId() != null) {
            JobApplication jobApp = modelMapper.map(jobApplicationService.getJobApplicationById(userId, request.getJobApplicationId()), JobApplication.class);
            reminder.setJobApplication(jobApp);
        }
        Reminder saved = reminderRepository.save(reminder);
        return modelMapper.map(saved, ReminderDto.Response.class);
    }

    @Transactional(readOnly = true)
    public ReminderDto.Response getReminderById(Long userId, Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found with id: " + id));
        if (!reminder.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Reminder not found for this user");
        }
        return modelMapper.map(reminder, ReminderDto.Response.class);
    }

    @Transactional(readOnly = true)
    public List<ReminderDto.Response> getAllReminders(Long userId, int page, int size) {
        User user = userService.getUserById(userId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Reminder> pageResult = reminderRepository.findByUser(user, pageable);
        return pageResult.stream()
                .map(reminder -> modelMapper.map(reminder, ReminderDto.Response.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ReminderDto.Response updateReminder(Long userId, Long id, ReminderDto.Request request) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found with id: " + id));
        if (!reminder.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Reminder not found for this user");
        }
        modelMapper.map(request, reminder);
        if (request.getJobApplicationId() != null) {
            JobApplication jobApp = modelMapper.map(jobApplicationService.getJobApplicationById(userId, request.getJobApplicationId()), JobApplication.class);
            reminder.setJobApplication(jobApp);
        } else {
            reminder.setJobApplication(null);
        }
        Reminder updated = reminderRepository.save(reminder);
        return modelMapper.map(updated, ReminderDto.Response.class);
    }

    @Transactional
    public void deleteReminder(Long userId, Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found with id: " + id));
        if (!reminder.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Reminder not found for this user");
        }
        reminderRepository.delete(reminder);
    }

    @Transactional
    public Reminder markAsCompleted(Long id, Long userId) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found with id: " + id));
        if (!reminder.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Reminder not found for this user");
        }
        reminder.setCompleted(true);
        reminder.setUpdatedAt(LocalDateTime.now());
        return reminderRepository.save(reminder);
    }

    // The following methods are commented out because the corresponding repository methods do not exist:
    // public List<Reminder> getRemindersOrderedByDate(Long userId) { ... }
    // public List<Reminder> getRemindersBetweenDates(Long userId, LocalDateTime start, LocalDateTime end) { ... }
}


































