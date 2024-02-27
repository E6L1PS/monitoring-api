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
import ru.ylab.application.exception.MeterTypeAlreadyExistsException;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.domain.model.MeterType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Создан: 28.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class MeterTypeServiceImplTest {

    private static MeterTypeDto meterTypeDto;

    private static MeterType meterType;

    private static MeterTypeEntity meterTypeEntity;

    private static List<MeterTypeDto> meterTypesDto;

    private static List<MeterType> meterTypes;

    private static List<MeterTypeEntity> meterTypeEntities;

    @Mock
    private MeterTypeRepository meterTypeRepository;

    @Mock
    private MeterTypeMapper meterTypeMapper;

    @InjectMocks
    private MeterTypeServiceImpl meterTypeService;

    @BeforeAll
    static void setUp() {
        meterTypeEntities = List.of(MeterTypeEntity.builder().name("Электричество").build());
        meterTypeDto = new MeterTypeDto("Электричество");
        MeterTypeMapper mapper = Mappers.getMapper(MeterTypeMapper.class);
        meterType = mapper.toDomain(meterTypeDto);
        meterTypeEntity = mapper.toEntity(meterType);
        meterTypes = mapper.toListDomain(meterTypeEntities);
        meterTypesDto = mapper.toListDto(meterTypes);
    }

    @Test
    void getAll() {

        when(meterTypeRepository.findAll()).thenReturn(meterTypeEntities);
        when(meterTypeMapper.toListDomain(anyList())).thenReturn(meterTypes);
        when(meterTypeMapper.toListDto(anyList())).thenReturn(meterTypesDto);

        List<MeterTypeDto> meterTypesDto = meterTypeService.getAll();

        assertThat(meterTypesDto).hasSize(1);
        assertThat(meterTypesDto).isNotNull();
        verify(meterTypeRepository).findAll();
    }

    @Test
    void save() {
        when(meterTypeRepository.isMeterTypeExists(anyString())).thenReturn(false);
        when(meterTypeMapper.toDomain(any(MeterTypeDto.class))).thenReturn(meterType);
        when(meterTypeMapper.toEntity(any(MeterType.class))).thenReturn(meterTypeEntity);

        meterTypeService.save(meterTypeDto);

        verify(meterTypeRepository).isMeterTypeExists(anyString());
        verify(meterTypeRepository).save(any(MeterTypeEntity.class));
    }

    @Test
    void save_ThrowsMeterTypeAlreadyExistsException() {
        when(meterTypeRepository.isMeterTypeExists(anyString())).thenReturn(true);

        assertThatThrownBy(() -> meterTypeService.save(meterTypeDto))
                .isInstanceOf(MeterTypeAlreadyExistsException.class);
    }

}