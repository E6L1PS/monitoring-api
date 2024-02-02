package ru.ylab.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.model.UtilityMeterModel;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;

@Mapper
public interface UtilityMeterMapper {
    UtilityMeterMapper INSTANCE = Mappers.getMapper(UtilityMeterMapper.class);

    UtilityMeter toUtilityMeter(UtilityMeterEntity utilityMeterEntity);

    List<UtilityMeterModel> entitiesToListUtilityMeterModel(List<UtilityMeterEntity> utilityMeterEntities);

    List<UtilityMeter> toListUtilityMeter(List<UtilityMeterEntity> utilityMeterEntities);
}
