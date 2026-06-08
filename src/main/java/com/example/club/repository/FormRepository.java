package com.example.club.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.club.model.Form;

public interface FormRepository extends JpaRepository<Form, String> {

    List<Form> findByClubId(String clubId);
}