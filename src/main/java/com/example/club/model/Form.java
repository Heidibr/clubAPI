package com.example.club.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;



@Entity
@Table(name = "forms")
public class Form {

    @Id
    private String formId;
    private String clubId;
    private String title;
    private Instant registrationOpens;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "form_member_types",
                     joinColumns = @JoinColumn(name = "form_id"))
                     
    private List<MemberTypeEntity> memberTypes = new ArrayList<>();

    protected Form() { }

    public Form(String formId, String clubId, String title,
                Instant registrationOpens, List<MemberTypeEntity> memberTypes) {
        this.formId = formId; this.clubId = clubId; 
        this.title = title;
        this.registrationOpens = registrationOpens; 
        this.memberTypes = memberTypes;
    }

    public String getFormId() {
        return formId;
    }

    public String getClubId() {
        return clubId;
    }

    public String getTitle() {
        return title;
    }

    public Instant getRegistrationOpens() {
        return registrationOpens;
    }

    public List<MemberTypeEntity> getMemberTypes() {
        return memberTypes;
    }
    
}


