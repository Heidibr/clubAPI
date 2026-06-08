package com.example.club.model;


import jakarta.persistence.Embeddable;

@Embeddable
public class MemberTypeEntity {
    private String id;
    private String name;

    protected MemberTypeEntity() { }
    public MemberTypeEntity(String id, String name) { this.id = id; this.name = name; }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}