package com.example.club.dto;

import java.time.Instant;
import java.time.LocalDate;


public record RegistrationDto(
    String id,
    String clubId,
    String formId,
    String memberTypeId,
    String username,
    String email,
    String phoneNumber,
    LocalDate dateOfBirth,
    Instant submittedAt
) {}