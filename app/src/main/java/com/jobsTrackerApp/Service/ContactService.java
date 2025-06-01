package com.jobsTrackerApp.Service;

import com.jobsTrackerApp.DTO.ContactDto;
import com.jobsTrackerApp.Exception.ResourceNotFoundException;
import com.jobsTrackerApp.Model.Contact;
import com.jobsTrackerApp.Model.JobApplication;
import com.jobsTrackerApp.Repository.ContactRepository;
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
public class ContactService {
    private final ContactRepository contactRepository;
    private final JobApplicationService jobApplicationService;
    private final ModelMapper modelMapper;

    @Transactional
    public ContactDto.Response createContact(Long userId, Long jobApplicationId, ContactDto.Request request) {
        JobApplication jobApplication = modelMapper.map(jobApplicationService.getJobApplicationById(userId, jobApplicationId), JobApplication.class);
        Contact contact = modelMapper.map(request, Contact.class);
        contact.setJobApplication(jobApplication);
        Contact saved = contactRepository.save(contact);
        return modelMapper.map(saved, ContactDto.Response.class);
    }

    @Transactional(readOnly = true)
    public ContactDto.Response getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
        return modelMapper.map(contact, ContactDto.Response.class);
    }

    @Transactional(readOnly = true)
    public List<ContactDto.Response> getAllContactsByJobApplication(Long jobApplicationId) {
        List<Contact> contacts = contactRepository.findByJobApplicationId(jobApplicationId);
        return contacts.stream()
                .map(contact -> modelMapper.map(contact, ContactDto.Response.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ContactDto.Response updateContact(Long id, ContactDto.Request request) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
        modelMapper.map(request, contact);
        Contact updated = contactRepository.save(contact);
        return modelMapper.map(updated, ContactDto.Response.class);
    }

    @Transactional
    public void deleteContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
        contactRepository.delete(contact);
    }
}