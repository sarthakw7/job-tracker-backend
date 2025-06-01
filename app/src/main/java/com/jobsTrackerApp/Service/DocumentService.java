package com.jobsTrackerApp.Service;

import com.jobsTrackerApp.DTO.DocumentDto;
import com.jobsTrackerApp.Exception.ResourceNotFoundException;
import com.jobsTrackerApp.Model.Document;
import com.jobsTrackerApp.Model.JobApplication;
import com.jobsTrackerApp.Repository.DocumentRepository;
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
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final JobApplicationService jobApplicationService;
    private final ModelMapper modelMapper;

    @Transactional
    public DocumentDto.Response createDocument(Long userId, Long jobApplicationId, DocumentDto.Request request) {
        JobApplication jobApplication = modelMapper.map(jobApplicationService.getJobApplicationById(userId, jobApplicationId), JobApplication.class);
        Document document = modelMapper.map(request, Document.class);
        document.setJobApplication(jobApplication);
        Document saved = documentRepository.save(document);
        return modelMapper.map(saved, DocumentDto.Response.class);
    }

    @Transactional(readOnly = true)
    public DocumentDto.Response getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        return modelMapper.map(document, DocumentDto.Response.class);
    }

    @Transactional(readOnly = true)
    public List<DocumentDto.Response> getAllDocumentsByJobApplication(Long jobApplicationId) {
        List<Document> documents = documentRepository.findByJobApplicationId(jobApplicationId);
        return documents.stream()
                .map(doc -> modelMapper.map(doc, DocumentDto.Response.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public  DocumentDto.Response updateDocument(Long id, DocumentDto.Request request) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " +id));
        modelMapper.map(request, document);
        Document updated = documentRepository.save(document);
        return modelMapper.map(updated, DocumentDto.Response.class);
    }

    @Transactional
    public void deleteDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Document not found with id: " + id));
        documentRepository.delete(document);
    }
}





















