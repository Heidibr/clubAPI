package com.example.club.model;

import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "registrations",
       uniqueConstraints = @UniqueConstraint(columnNames = {"form_id", "email"}))
public class Registration {

    @Id
    private String id;
    private String clubId;
    private String formId;
    private String memberTypeId;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Instant submittedAt;

    protected Registration() { }   // required by JPA

    public Registration(String id, String clubId, String formId, String memberTypeId,
                        String username, String email, String phoneNumber,
                        LocalDate dateOfBirth, Instant submittedAt) {
        this.id = id; this.clubId = clubId; this.formId = formId;
        this.memberTypeId = memberTypeId; this.username = username;
        this.email = email; this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth; this.submittedAt = submittedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getMemberTypeId() {
        return memberTypeId;
    }

    public void setMemberTypeId(String memberTypeId) {
        this.memberTypeId = memberTypeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
    }

    
}


