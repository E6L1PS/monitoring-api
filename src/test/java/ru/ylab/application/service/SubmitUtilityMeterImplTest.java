package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.exception.MonthlySubmitLimitException;
import ru.ylab.application.exception.NotValidMeterTypeException;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmitUtilityMeterImplTest {

    private static List<UtilityMeterDto> utilityMetersDto;

    private static List<UtilityMeter> utilityMeters;

    private static UtilityMeterEntity utilityMeterEntity;

    private static Map<String, Double> validMeters;

    private static Map<String, Double> notValidMeters;

    private static List<MeterTypeEntity> meterTypes;

    private static Long userId;

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private MeterTypeRepository meterTypeRepository;

    @InjectMocks
    private SubmitUtilityMeterImpl submitUtilityMeter;

    @BeforeAll
    static void setUp() {
        UtilityMeterMapper mapper = Mappers.getMapper(UtilityMeterMapper.class);
        utilityMeterEntity = UtilityMeterEntity.builder().build();
        utilityMeters = mapper.toListDomain(List.of(utilityMeterEntity));
        utilityMetersDto = mapper.toListDto(utilityMeters);
        validMeters = Map.of("Холодная вода", 100.0);
        notValidMeters = Map.of("asdadsadasdа", 100.0);
        userId = 1L;
        meterTypes = List.of(MeterTypeEntity.builder().name("Холодная вода").build());
    }

    @Test
    @DisplayName("Если в этом месяце еще не подавали показания")
    void execute_Success() {
        when(meterRepository.isSubmitted(any())).thenReturn(false);
        when(meterTypeRepository.findAll()).thenReturn(meterTypes);
        doNothing().when(meterRepository).saveAll(anyList());

        submitUtilityMeter.execute(validMeters, userId);

        verify(meterRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("Если в этом месяце уже подавали показания")
    void execute_ThrowsMonthlySubmitLimitException() {
        when(meterRepository.isSubmitted(any())).thenReturn(true);

        assertThatThrownBy(() -> submitUtilityMeter.execute(validMeters, userId))
                .isInstanceOf(MonthlySubmitLimitException.class);
    }

    @Test
    @DisplayName("Если поданы не валидные данные")
    void execute_ThrowsNotValidMeterTypeException() {
        when(meterRepository.isSubmitted(any())).thenReturn(false);
        when(meterTypeRepository.findAll()).thenReturn(meterTypes);

        assertThatThrownBy(() -> submitUtilityMeter.execute(notValidMeters, userId))
                .isInstanceOf(NotValidMeterTypeException.class);
    }

}