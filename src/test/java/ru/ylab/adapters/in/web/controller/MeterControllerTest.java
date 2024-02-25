package ru.ylab.adapters.in.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.application.in.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Создан: 24.02.2024.
 *
 * @author Pesternikov Danil
 */
@DisplayName("Тест MeterController")
@ExtendWith(MockitoExtension.class)
class MeterControllerTest {

    @Mock
    private GetAllUtilityMeter getAllUtilityMeter;

    @Mock
    private GetAllUtilityMeterById getAllUtilityMeterById;

    @Mock
    private GetUtilityMeterByMonth getUtilityMeterByMonth;

    @Mock
    private GetLastUtilityMeter getLastUtilityMeter;

    @Mock
    private SubmitUtilityMeter submitUtilityMeter;

    @InjectMocks
    private MeterController meterController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private List<UtilityMeterDto> utilityMeterDtoList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(meterController).build();
        objectMapper = new ObjectMapper();
        utilityMeterDtoList = List.of(
                new UtilityMeterDto(
                        1L,
                        1L,
                        123.0,
                        "asd",
                        LocalDate.of(2001, 10, 10)
                ));
    }

    @Test
    void getAll_ReturnsResponseEntity() throws Exception {
        when(getAllUtilityMeter.execute()).thenReturn(utilityMeterDtoList);

        mockMvc.perform(get("/meter/all")
                        .with(user("username").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].counter").value(123.0))
                .andExpect(jsonPath("$[0].type").value("asd"))
                .andExpect(jsonPath("$[0].readingsDate").value("2001-10-10"));

        verify(getAllUtilityMeter).execute();
    }

    @Test
    void getAllById_ReturnsResponseEntity() throws Exception {
        when(getAllUtilityMeterById.execute(null)).thenReturn(utilityMeterDtoList);

        mockMvc.perform(get("/meter")
                        .with(user("username").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].counter").value(123.0))
                .andExpect(jsonPath("$[0].type").value("asd"))
                .andExpect(jsonPath("$[0].readingsDate").value("2001-10-10"));

        verify(getAllUtilityMeterById).execute(null);
    }

    @Test
    void getAllByMonth_ReturnsResponseEntity() throws Exception {
        when(getUtilityMeterByMonth.execute(3, null)).thenReturn(utilityMeterDtoList);

        mockMvc.perform(get("/meter/month/{number}", 3)
                        .with(user("username").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].counter").value(123.0))
                .andExpect(jsonPath("$[0].type").value("asd"))
                .andExpect(jsonPath("$[0].readingsDate").value("2001-10-10"));

        verify(getUtilityMeterByMonth).execute(3, null);
    }

    @Test
    void getLast_ReturnsResponseEntity() throws Exception {
        when(getLastUtilityMeter.execute(null)).thenReturn(utilityMeterDtoList);

        mockMvc.perform(get("/meter/last")
                        .with(user("username").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].counter").value(123.0))
                .andExpect(jsonPath("$[0].type").value("asd"))
                .andExpect(jsonPath("$[0].readingsDate").value("2001-10-10"));

        verify(getLastUtilityMeter).execute(null);
    }

    @Test
    void save_ReturnsResponseEntity() throws Exception {
        Map<String, Double> meters = Map.of("asd", 123.0);
        String s = objectMapper.writeValueAsString(meters);

        mockMvc.perform(post("/meter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andExpect(status().isCreated());

        verify(submitUtilityMeter).execute(meters, null);
    }
}