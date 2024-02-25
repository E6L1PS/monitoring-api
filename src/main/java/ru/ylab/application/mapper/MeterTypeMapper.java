package ru.ylab.application.mapper;

import org.mapstruct.Mapper;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.domain.model.MeterType;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MeterTypeMapper {

    MeterType toDomain(MeterTypeEntity meterTypeEntity);

    MeterType toDomain(MeterTypeDto meterTypeDto);

    MeterTypeEntity toEntity(MeterType meterType);

    List<MeterType> toListDomain(List<MeterTypeEntity> meterTypeEntities);

    List<MeterTypeDto> toListDto(List<MeterType> meterTypes);

}
