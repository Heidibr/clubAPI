package com.example.club.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.club.dto.RegistrationDto;
import com.example.club.dto.UserRegistration;
import com.example.club.model.Registration;
import com.example.club.repository.UserRegistrationRepository;


@Service
public class UserRegistrationService {

    private static final String ALREADY_REGISTERED = "Already registered";

    private final UserRegistrationRepository userRegistrationRepository;

    public UserRegistrationService(UserRegistrationRepository userRegistrationRepository) {
        this.userRegistrationRepository = userRegistrationRepository;
    }

    public RegistrationDto register(String clubId, String formId, UserRegistration req) {
        if (userRegistrationRepository.existsByFormIdAndEmail(formId, req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ALREADY_REGISTERED);
        }

        Registration entity = new Registration(
            UUID.randomUUID().toString(), clubId, formId, req.memberTypeId(),
            req.firstName() + " " + req.lastName(), req.email(), req.phoneNumber(),
            req.dateOfBirth(), Instant.now());

        try {
            return toDto(userRegistrationRepository.save(entity));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ALREADY_REGISTERED);
        }
    }

    public List<RegistrationDto> all() {
        return userRegistrationRepository.findAll().stream()
            .map(this::toDto)
            .toList();
    }

    private RegistrationDto toDto(Registration r) {
        return new RegistrationDto(
            r.getId(), r.getClubId(), r.getFormId(), r.getMemberTypeId(),
            r.getUsername(), r.getEmail(), r.getPhoneNumber(),
            r.getDateOfBirth(), r.getSubmittedAt());
    }

}
