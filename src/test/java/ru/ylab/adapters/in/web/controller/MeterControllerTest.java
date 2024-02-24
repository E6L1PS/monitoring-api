package ru.ylab.adapters.in.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.application.in.*;
import ru.ylab.domain.model.User;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Создан: 24.02.2024.
 *
 * @author Pesternikov Danil
 */
@DisplayName("Тест MeterController")
@ExtendWith(MockitoExtension.class)
class MeterControllerTest {

    @Mock
    GetAllUtilityMeter getAllUtilityMeter;

    @Mock
    GetAllUtilityMeterById getAllUtilityMeterById;

    @Mock
    GetUtilityMeterByMonth getUtilityMeterByMonth;

    @Mock
    GetLastUtilityMeter getLastUtilityMeter;

    @Mock
    SubmitUtilityMeter submitUtilityMeter;

    @Mock
    List<UtilityMeterDto> utilityMeterDtoList;

    User user = User.builder().id(1L).build();

    @InjectMocks
    MeterController meterController;

    @Test
    void getAll_ReturnsResponseEntity() {
        when(getAllUtilityMeter.execute()).thenReturn(utilityMeterDtoList);

        var responseEntity = meterController.getAll();

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(utilityMeterDtoList);
    }

    @Test
    void getAllById_ReturnsResponseEntity() {
        when(getAllUtilityMeterById.execute(anyLong())).thenReturn(utilityMeterDtoList);

        var responseEntity = meterController.getAllById(user);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(utilityMeterDtoList);
    }

    @Test
    void getAllByMonth_ReturnsResponseEntity() {
        when(getUtilityMeterByMonth.execute(anyInt(), anyLong())).thenReturn(utilityMeterDtoList);

        var responseEntity = meterController.getAllByMonth(3, user);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(utilityMeterDtoList);
    }

    @Test
    void getLast_ReturnsResponseEntity() {
        when(getLastUtilityMeter.execute(anyLong())).thenReturn(utilityMeterDtoList);

        var responseEntity = meterController.getLast(user);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(utilityMeterDtoList);
    }

    @Test
    void save_ReturnsResponseEntity() {
        Map<String, Double> meters = Map.of("asd", 123.0);
        when(submitUtilityMeter.execute(anyMap(), anyLong())).thenReturn(utilityMeterDtoList);

        var responseEntity = meterController.save(meters, user);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo(utilityMeterDtoList);
    }
}