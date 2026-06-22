package com.example.club.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.club.dto.RegistrationDto;
import com.example.club.dto.UserRegistration;
import com.example.club.service.UserRegistrationService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/clubs/{clubId}/forms/{formId}")
public class UserController {
    private final UserRegistrationService userService;

    public UserController(UserRegistrationService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Add user")
    @PostMapping("/registrations")
    public ResponseEntity<RegistrationDto> register(
            @PathVariable String clubId,
            @PathVariable String formId,
            @Valid @RequestBody UserRegistration request) {

        RegistrationDto saved = userService.register(clubId, formId, request);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.id())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }
    

}