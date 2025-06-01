package com.jobsTrackerApp.Service;

import com.jobsTrackerApp.DTO.ApplicationStageDto;
import com.jobsTrackerApp.Exception.ResourceNotFoundException;
import com.jobsTrackerApp.Model.ApplicationStage;
import com.jobsTrackerApp.Model.JobApplication;
import com.jobsTrackerApp.Repository.ApplicationStageRepository;
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
public class ApplicationStageService {
    private final ApplicationStageRepository applicationStageRepository;
    private final JobApplicationService jobApplicationService;
    private final ModelMapper modelMapper;

    @Transactional
    public ApplicationStageDto.Response createStage(Long userId, Long jobApplicationId, ApplicationStageDto.Request request) {
        JobApplication jobApplication = modelMapper.map(jobApplicationService.getJobApplicationById(userId, jobApplicationId), JobApplication.class);
        ApplicationStage stage = modelMapper.map(request, ApplicationStage.class);
        stage.setJobApplication(jobApplication);
        ApplicationStage saved = applicationStageRepository.save(stage);
        return modelMapper.map(saved, ApplicationStageDto.Response.class);
    }

    @Transactional(readOnly = true)
    public ApplicationStageDto.Response getStageById(Long userId, Long id) {
        ApplicationStage stage = applicationStageRepository.findByIdAndJobApplicationUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found with id: " + id));
        return modelMapper.map(stage, ApplicationStageDto.Response.class);
    }

    @Transactional(readOnly = true)
    public List<ApplicationStageDto.Response> getAllStagesByJobApplication(Long userId, Long jobApplicationId) {
        List<ApplicationStage> stages = applicationStageRepository.findByJobApplicationIdOrderByStageDateAsc(jobApplicationId);
        return stages.stream()
                .map(stage -> modelMapper.map(stage, ApplicationStageDto.Response.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ApplicationStageDto.Response updateStage(Long userId, Long id, ApplicationStageDto.Request request) {
        ApplicationStage stage = applicationStageRepository.findByIdAndJobApplicationUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found with id: " + id));
        modelMapper.map(request, stage);
        ApplicationStage updated = applicationStageRepository.save(stage);
        return modelMapper.map(updated, ApplicationStageDto.Response.class);
    }

    @Transactional
    public void deleteStage(Long userId, Long id) {
        ApplicationStage stage = applicationStageRepository.findByIdAndJobApplicationUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found with id: " + id));
        applicationStageRepository.delete(stage);
    }

    @Transactional
    public ApplicationStageDto.Response updateStageStatus(Long userId, Long id, ApplicationStageDto.StatusUpdateRequest request) {
        ApplicationStage stage = applicationStageRepository.findByIdAndJobApplicationUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found with id: " + id));
        stage.setStatus(request.getStatus());
        ApplicationStage updated = applicationStageRepository.save(stage);
        return modelMapper.map(updated, ApplicationStageDto.Response.class);
    }
}