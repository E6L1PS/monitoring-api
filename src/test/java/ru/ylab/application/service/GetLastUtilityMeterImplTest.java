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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetLastUtilityMeterImplTest {

    private static List<UtilityMeterDto> utilityMetersDto;

    private static List<UtilityMeter> utilityMeters;

    private static List<UtilityMeterEntity> utilityMeterEntities;

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private UtilityMeterMapper utilityMeterMapper;

    @InjectMocks
    private GetLastUtilityMeterImpl getLastUtilityMeter;

    @BeforeAll
    static void setUp() {
        utilityMeterEntities = List.of(UtilityMeterEntity.builder().build());
        UtilityMeterMapper mapper = Mappers.getMapper(UtilityMeterMapper.class);
        utilityMeters = mapper.toListDomain(utilityMeterEntities);
        utilityMetersDto = mapper.toListDto(utilityMeters);
    }

    @Test
    void execute() {
        when(meterRepository.findLastByUserId(anyLong())).thenReturn(utilityMeterEntities);
        when(utilityMeterMapper.toListDomain(anyList())).thenReturn(utilityMeters);
        when(utilityMeterMapper.toListDto(anyList())).thenReturn(utilityMetersDto);

        List<UtilityMeterDto> utilityMetersDto = getLastUtilityMeter.execute(1L);

        assertThat(utilityMetersDto).hasSize(1);
        assertThat(utilityMetersDto).isNotNull();
        verify(meterRepository).findLastByUserId(anyLong());
    }

}