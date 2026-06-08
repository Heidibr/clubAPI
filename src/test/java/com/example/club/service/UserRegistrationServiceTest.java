package com.example.club.service;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.club.dto.RegistrationDto;
import com.example.club.dto.UserRegistration;
import com.example.club.model.Registration;
import com.example.club.repository.UserRegistrationRepository;

@ExtendWith(MockitoExtension.class)
class UserRegistrationServiceTest {

    @Mock
    private UserRegistrationRepository repository;

    @InjectMocks
    private UserRegistrationService service;

    private UserRegistration sampleRequest() {
        return new UserRegistration(
            "Ada", "Lovelace", "ada@example.com",
            "member-1", LocalDate.of(1990, 5, 1), "12345678");
    }

    @Test
    void registerSavesRegistrationAndReturnsDtoWhenEmailIsNew() {
        // given
        when(repository.existsByFormIdAndEmail("form-1", "ada@example.com")).thenReturn(false);
        // save returns its argument back, like a real JPA save would
        when(repository.save(any(Registration.class))).thenAnswer(inv -> inv.getArgument(0));

        // when
        RegistrationDto result = service.register("britsport", "form-1", sampleRequest());

        // then
        assertThat(result.username()).isEqualTo("Ada Lovelace");
        assertThat(result.email()).isEqualTo("ada@example.com");
        assertThat(result.clubId()).isEqualTo("britsport");
        assertThat(result.formId()).isEqualTo("form-1");
        assertThat(result.id()).isNotBlank(); // service generates a UUID
        verify(repository).save(any(Registration.class));
    }

    @Test
    void registerThrowsConflictWhenEmailAlreadyRegistered() {
        // given
        when(repository.existsByFormIdAndEmail("form-1", "ada@example.com")).thenReturn(true);

        // when / then
        assertThatThrownBy(() -> service.register("britsport", "form-1", sampleRequest()))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Already registered")
            .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
            .isEqualTo(HttpStatus.CONFLICT);

        verify(repository, never()).save(any());
    }
}