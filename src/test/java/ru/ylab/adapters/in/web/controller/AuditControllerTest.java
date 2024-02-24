package ru.ylab.adapters.in.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.ylab.adapters.in.web.dto.AuditDto;
import ru.ylab.application.in.GetAuditInfo;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Создан: 23.02.2024.
 *
 * @author Pesternikov Danil
 */
@DisplayName("Тест AuditController")
@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

    @Mock
    GetAuditInfo getAuditInfo;

    @InjectMocks
    AuditController auditController;

    @Test
    void getAll_ReturnsResponseEntity() {
        List<AuditDto> audits = List.of(
                new AuditDto(1L,
                        1L,
                        "test",
                        LocalDateTime.of(1, 1, 1, 1, 1)
                ));
        when(getAuditInfo.execute()).thenReturn(audits);

        var responseEntity = auditController.getAll();

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(audits);
    }

}