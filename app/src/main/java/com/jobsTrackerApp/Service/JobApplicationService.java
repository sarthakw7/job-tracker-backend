package com.jobsTrackerApp.Service;

import com.jobsTrackerApp.DTO.JobApplicationDto;
import com.jobsTrackerApp.Exception.ResourceNotFoundException;
import com.jobsTrackerApp.Model.JobApplication;
import com.jobsTrackerApp.Model.User;
import com.jobsTrackerApp.Repository.JobApplicationRepository;
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
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public JobApplicationDto.Response createJobApplication(Long userId, JobApplicationDto.Request request) {
        User user = userService.getUserById(userId);
        JobApplication jobApplication = modelMapper.map(request, JobApplication.class);
        jobApplication.setUser(user);
        JobApplication saved = jobApplicationRepository.save(jobApplication);
        return modelMapper.map(saved, JobApplicationDto.Response.class);
    }

    @Transactional(readOnly = true)
    public JobApplicationDto.Response getJobApplicationById(Long userId, Long id) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));
        if (!jobApplication.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Job application not found for this user");
        }
        return modelMapper.map(jobApplication, JobApplicationDto.Response.class);
    }

    @Transactional(readOnly = true)
    public List<JobApplicationDto.Response> getAllJobApplications(Long userId, int page, int size) {
        User user = userService.getUserById(userId);
        Pageable pageable = PageRequest.of(page, size);
        Page<JobApplication> pageResult = jobApplicationRepository.findByUser(user, pageable);
        return pageResult.stream()
                .map(app -> modelMapper.map(app, JobApplicationDto.Response.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public JobApplicationDto.Response updateJobApplication(Long userId, Long id, JobApplicationDto.Request request) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));
        if (!jobApplication.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Job application not found for this user");
        }
        modelMapper.map(request, jobApplication);
        JobApplication updated = jobApplicationRepository.save(jobApplication);
        return modelMapper.map(updated, JobApplicationDto.Response.class);
    }

    @Transactional
    public void deleteJobApplication(Long userId, Long id) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));
        if (!jobApplication.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Job application not found for this user");
        }
        jobApplicationRepository.delete(jobApplication);
    }

    @Transactional(readOnly = true)
    public Page<JobApplication> getUserApplications(Long userId, Pageable pageable) {
        User user = userService.getUserById(userId);
        return jobApplicationRepository.findByUser(user, pageable);
    }

    @Transactional(readOnly = true)
    public List<JobApplication> getApplicationsByStatus(Long userId, JobApplication.JobApplicationStatus status) {
        User user = userService.getUserById(userId);
        return jobApplicationRepository.findByUserAndStatus(user, status, Pageable.unpaged()).getContent();
    }

    public long countUserApplications(Long userId) {
        User user = userService.getUserById(userId);
        return jobApplicationRepository.countByUserAndStatus(user, null);
    }

    public long countApplicationsByStatus(Long userId, JobApplication.JobApplicationStatus status) {
        User user = userService.getUserById(userId);
        return jobApplicationRepository.countByUserAndStatus(user, status);
    }
}
