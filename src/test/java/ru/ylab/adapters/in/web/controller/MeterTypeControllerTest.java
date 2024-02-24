package ru.ylab.adapters.in.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.in.GetUtilityMeterTypes;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Создан: 24.02.2024.
 *
 * @author Pesternikov Danil
 */
@DisplayName("Тест MeterTypeController")
@ExtendWith(MockitoExtension.class)
class MeterTypeControllerTest {

    @Mock
    GetUtilityMeterTypes getUtilityMeterTypes;

    @Mock
    AddNewMeterType addNewMeterType;

    @InjectMocks
    MeterTypeController meterTypeController;

    @Test
    void getAll_ReturnsResponseEntity() {
        List<MeterTypeDto> meterTypeDtoList = List.of(new MeterTypeDto("name"));
        when(getUtilityMeterTypes.execute()).thenReturn(meterTypeDtoList);

        var responseEntity = meterTypeController.getAll();

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(meterTypeDtoList);
    }

    @Test
    void save_ReturnsResponseEntity() {
        MeterTypeDto meterTypeDto = new MeterTypeDto("name");

        var responseEntity = meterTypeController.save(meterTypeDto);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}