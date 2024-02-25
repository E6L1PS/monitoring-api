package ru.ylab.adapters.in.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.ylab.adapters.in.web.dto.AuditDto;
import ru.ylab.application.in.GetAuditInfo;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Создан: 23.02.2024.
 *
 * @author Pesternikov Danil
 */
@DisplayName("Тест AuditController")
@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

    @Mock
    private GetAuditInfo getAuditInfo;

    @InjectMocks
    private AuditController auditController;

    private MockMvc mockMvc;

    private List<AuditDto> audits;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(auditController).build();
        audits = List.of(
                new AuditDto(1L,
                        1L,
                        "test",
                        LocalDateTime.of(1, 1, 1, 1, 1)
                ));
    }

    @Test
    void getAll_ReturnsResponseEntity() throws Exception {
        when(getAuditInfo.execute()).thenReturn(audits);

        mockMvc.perform(get("/audit")
                        .with(user("username").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].info").value("test"))
                .andExpect(jsonPath("$[0].dateTime").value("0001-01-01 01:01:00"));

        verify(getAuditInfo).execute();
    }

}