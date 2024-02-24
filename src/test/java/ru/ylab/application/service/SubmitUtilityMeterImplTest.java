package ru.ylab.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.exception.MonthlySubmitLimitException;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.domain.model.UtilityMeter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmitUtilityMeterImplTest {

    static List<UtilityMeterDto> utilityMetersDto;

    static List<UtilityMeter> utilityMeters;

    static UtilityMeterEntity utilityMeterEntity;

    @Mock
    MeterRepository meterRepository;

    @Mock
    MeterTypeRepository meterTypeRepository;

    @Mock
    UtilityMeterMapper utilityMeterMapper;

    @InjectMocks
    SubmitUtilityMeterImpl submitUtilityMeter;

    Map<String, Double> meters;

    Long userId = 1L;

    @BeforeAll
    static void setup() {
        UtilityMeterMapper mapper = Mappers.getMapper(UtilityMeterMapper.class);
        utilityMeterEntity = UtilityMeterEntity.builder().build();
        utilityMeters = mapper.toListDomain(List.of(utilityMeterEntity));
        utilityMetersDto = mapper.toListDto(utilityMeters);
    }

    @BeforeEach
    void setUp() {
        meters = new HashMap<>();
        meters.put("Холодная вода", 100.0);
    }

    @Test
    @DisplayName("Если в этом месяце еще не подавали показания")
    void testExecuteWhenMonthlyLimitNotExceeded() {
        int month = LocalDate.now().getMonthValue();
        when(meterRepository.isSubmitted(any())).thenReturn(false);
        when(meterTypeRepository.isMeterTypeExists(any())).thenReturn(true);
        when(meterRepository.save(any(UtilityMeterEntity.class))).thenReturn(utilityMeterEntity);

        submitUtilityMeter.execute(meters, userId);

        verify(meterRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Если в этом месяце уже подавали показания")
    void testExecuteWhenMonthlyLimitExceeded() {
        when(meterRepository.isSubmitted(any())).thenReturn(true);

        Assertions.assertThatThrownBy(() -> submitUtilityMeter.execute(meters, userId))
                .isInstanceOf(MonthlySubmitLimitException.class);
    }
}