package ru.ylab.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylab.adapters.in.web.dto.MeterTypeModel;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.domain.model.MeterType;

import java.util.List;

@Mapper
public interface MeterTypeMapper {

    MeterTypeMapper INSTANCE = Mappers.getMapper(MeterTypeMapper.class);

    MeterType toMeterType(MeterTypeEntity meterTypeEntity);

    MeterType toMeterType(MeterTypeModel meterTypeModel);

    MeterTypeEntity toMeterTypeEntity(MeterType meterType);

    List<MeterType> entitiesToListMeterType(List<MeterTypeEntity> meterTypeEntities);

    List<MeterTypeModel> metersToListMeterTypeModel(List<MeterType> meterTypes);
}
