package com.example.club.dto;

import java.time.Instant;
import java.util.List;


public record FormDto(
    String clubId,
    List<MemberType> memberTypes,
    String formId,
    String title,
    Instant registrationOpens
) {
  public record MemberType(
      String id,
      String name
  ) {}
}
