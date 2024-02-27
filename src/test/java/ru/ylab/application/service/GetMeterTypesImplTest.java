package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.domain.model.MeterType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Создан: 27.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class GetMeterTypesImplTest {

    private static List<MeterTypeDto> meterTypesDto;

    private static List<MeterType> meterTypes;

    private static List<MeterTypeEntity> meterTypeEntities;

    @Mock
    private MeterTypeRepository meterTypeRepository;

    @Mock
    private MeterTypeMapper meterTypeMapper;

    @InjectMocks
    private GetMeterTypesImpl getUtilityMeterTypes;

    @BeforeAll
    static void setUp() {
        meterTypeEntities = List.of(MeterTypeEntity.builder().name("test").build());
        MeterTypeMapper mapper = Mappers.getMapper(MeterTypeMapper.class);
        meterTypes = mapper.toListDomain(meterTypeEntities);
        meterTypesDto = mapper.toListDto(meterTypes);
    }

    @Test
    void execute_Success() {
        when(meterTypeRepository.findAll()).thenReturn(meterTypeEntities);
        when(meterTypeMapper.toListDomain(anyList())).thenReturn(meterTypes);
        when(meterTypeMapper.toListDto(anyList())).thenReturn(meterTypesDto);

        List<MeterTypeDto> meterTypesDto = getUtilityMeterTypes.execute();

        assertThat(meterTypesDto).hasSize(1);
        assertThat(meterTypesDto).isNotNull();
        verify(meterTypeRepository).findAll();
    }

}