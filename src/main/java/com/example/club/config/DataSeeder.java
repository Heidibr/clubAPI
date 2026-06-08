package com.example.club.config;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.club.model.Form;
import com.example.club.model.MemberTypeEntity;
import com.example.club.repository.FormRepository;

@Configuration
@Profile("!prod")
public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private static final String FORM_ID = "B171388180BC457D9887AD92B6CCFC86";
    private static final String CLUB_ID = "britsport";
    private static final String TITLE = "Coding camp summer 2026";
    private static final Instant REGISTRATION_OPENS = Instant.parse("2024-12-16T00:00:00Z");
    private static final List<MemberTypeEntity> MEMBER_TYPES = List.of(
        new MemberTypeEntity("4237C55C5CC3B4B082CBF2540612778E", "Social Member"),
    new MemberTypeEntity("8FE4113D4E4020E0DCF887803A886981", "Active Member")
    );
    @Bean
    CommandLineRunner seedForm(FormRepository repository) {
        return args -> {
            if (repository.existsById(FORM_ID)) {
                log.info("Seed form '{}' already present — skipping seed.", FORM_ID);
                return;
            }
            repository.save(new Form(FORM_ID, CLUB_ID, TITLE, REGISTRATION_OPENS, MEMBER_TYPES));
            log.info("Seeded default form '{}' for club '{}'.", FORM_ID, CLUB_ID);
        };
    }
}
