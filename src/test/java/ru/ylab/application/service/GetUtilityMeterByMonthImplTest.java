package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Создан: 24.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class GetUtilityMeterByMonthImplTest {

    static List<UtilityMeterDto> utilityMetersDto;

    static List<UtilityMeter> utilityMeters;


    static List<UtilityMeterEntity> utilityMeterEntities;

    @Mock
    MeterRepository meterRepository;

    @Mock
    UtilityMeterMapper utilityMeterMapper;

    @InjectMocks
    GetUtilityMeterByMonthImpl getUtilityMeterByMonth;

    @BeforeAll
    static void setUp() {
        utilityMeterEntities = List.of(UtilityMeterEntity.builder().build());
        UtilityMeterMapper mapper = Mappers.getMapper(UtilityMeterMapper.class);
        utilityMeters = mapper.toListDomain(utilityMeterEntities);
        utilityMetersDto = mapper.toListDto(utilityMeters);
    }

    @Test
    void execute() {
        when(meterRepository.findByMonthAndUserId(anyInt(), anyLong())).thenReturn(utilityMeterEntities);
        when(utilityMeterMapper.toListDomain(anyList())).thenReturn(utilityMeters);
        when(utilityMeterMapper.toListDto(anyList())).thenReturn(utilityMetersDto);

        List<UtilityMeterDto> utilityMetersDto = getUtilityMeterByMonth.execute(1, 1L);

        assertThat(utilityMetersDto).hasSize(1);
        assertThat(utilityMetersDto).isNotNull();
        verify(meterRepository).findByMonthAndUserId(anyInt(), anyLong());
    }

}