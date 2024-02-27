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
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.in.GetMeterTypes;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
@DisplayName("Тест MeterTypeController")
@ExtendWith(MockitoExtension.class)
class MeterTypeControllerTest {

    @Mock
    private GetMeterTypes getMeterTypes;

    @Mock
    private AddNewMeterType addNewMeterType;

    @InjectMocks
    private MeterTypeController meterTypeController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private List<MeterTypeDto> meterTypeDtoList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(meterTypeController).build();
        objectMapper = new ObjectMapper();
        meterTypeDtoList = List.of(
                new MeterTypeDto("name1"),
                new MeterTypeDto("name2"),
                new MeterTypeDto("name3")
        );
    }

    @Test
    void getAll_ReturnsResponseEntity() throws Exception {
        when(getMeterTypes.execute()).thenReturn(meterTypeDtoList);

        mockMvc.perform(get("/type")
                        .with(user("username").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[2].name").value("name3"));

        verify(getMeterTypes).execute();
    }

    @Test
    void save_ReturnsResponseEntity() throws Exception {
        MeterTypeDto meterTypeDto = new MeterTypeDto("name");
        String s = objectMapper.writeValueAsString(meterTypeDto);

        mockMvc.perform(post("/type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
                        .with(user("username").roles("USER")))
                .andExpect(status().isCreated());

        verify(addNewMeterType).execute(any());
    }
}