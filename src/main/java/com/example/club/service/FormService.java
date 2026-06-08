package com.example.club.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.club.dto.FormDto;
import com.example.club.model.Form;
import com.example.club.model.MemberTypeEntity;
import com.example.club.repository.FormRepository;

@Service
public class FormService {

    private final FormRepository repository;

    public FormService(FormRepository repository) {
        this.repository = repository;
    }

    public FormDto create(FormDto dto) {
        Form form = new Form(
            dto.formId(), dto.clubId(), dto.title(), dto.registrationOpens(),
            dto.memberTypes().stream()
                .map(mt -> new MemberTypeEntity(mt.id(), mt.name()))
                .toList());
        return toDto(repository.save(form));
    }


    public FormDto getFormByClub(String clubId) {
        return repository.findByClubId(clubId).stream()
            .findFirst()
            .map(this::toDto)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));
    }

    private FormDto toDto(Form form) {
        return new FormDto(
            form.getClubId(),
            form.getMemberTypes().stream()
                .map(mt -> new FormDto.MemberType(mt.getId(), mt.getName()))
                .toList(),
            form.getFormId(),
            form.getTitle(),
            form.getRegistrationOpens());
    }
}