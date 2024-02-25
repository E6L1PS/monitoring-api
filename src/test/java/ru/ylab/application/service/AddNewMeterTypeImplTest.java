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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddNewMeterTypeImplTest {

    private static MeterTypeDto meterTypeDto;

    private static MeterType meterType;

    private static MeterTypeEntity meterTypeEntity;

    @Mock
    private MeterTypeRepository meterTypeRepository;

    @Mock
    private MeterTypeMapper meterTypeMapper;

    @InjectMocks
    private AddNewMeterTypeImpl addNewMeterType;

    @BeforeAll
    static void setUp() {
        meterTypeDto = new MeterTypeDto("Электричество");
        MeterTypeMapper mapper = Mappers.getMapper(MeterTypeMapper.class);
        meterType = mapper.toDomain(meterTypeDto);
        meterTypeEntity = mapper.toEntity(meterType);
    }

    @Test
    void test() {
        when(meterTypeMapper.toDomain(any(MeterTypeDto.class))).thenReturn(meterType);
        when(meterTypeMapper.toEntity(any(MeterType.class))).thenReturn(meterTypeEntity);

        addNewMeterType.execute(meterTypeDto);

        verify(meterTypeRepository).save(any(MeterTypeEntity.class));
    }

}