package ru.ylab.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.domain.model.MeterType;

import java.util.List;

@Mapper
public interface MeterTypeMapper {

    MeterTypeMapper INSTANCE = Mappers.getMapper(MeterTypeMapper.class);

    MeterType toDomain(MeterTypeEntity meterTypeEntity);

    MeterType toDomain(MeterTypeDto meterTypeDto);

    MeterTypeEntity toEntity(MeterType meterType);

    List<MeterType> toListDomain(List<MeterTypeEntity> meterTypeEntities);

    List<MeterTypeDto> toListDto(List<MeterType> meterTypes);

}
