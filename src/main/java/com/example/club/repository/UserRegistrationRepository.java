package com.example.club.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.club.model.Registration;

public interface UserRegistrationRepository extends JpaRepository<Registration, String> {

    boolean existsByFormIdAndEmail(String formId, String email);
    List<Registration> findByFormId(String formId);
}