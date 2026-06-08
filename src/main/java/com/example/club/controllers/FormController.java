package com.example.club.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.club.dto.FormDto;
import com.example.club.service.FormService;

import io.swagger.v3.oas.annotations.Operation;




@RestController
@RequestMapping("/clubs/{clubId}/forms")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }
    
    @Operation(summary = "Add form (only for demo and testing)")
    @PostMapping
    public ResponseEntity<FormDto> create(
            @PathVariable String clubId,
            @RequestBody FormDto form) {
        FormDto saved = formService.create(form);
        return ResponseEntity
            .created(URI.create("/clubs/" + clubId + "/forms/" + saved.formId()))
            .body(saved);
    }

    @Operation(summary = "Get the club's form")
    @GetMapping
    public FormDto getFormByClub(@PathVariable String clubId) {
        return formService.getFormByClub(clubId);
    }
}