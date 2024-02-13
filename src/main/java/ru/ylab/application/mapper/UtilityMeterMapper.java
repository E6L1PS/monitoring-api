package ru.ylab.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylab.adapters.in.web.dto.UtilityMeterModel;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;

import java.util.List;

@Mapper
public interface UtilityMeterMapper {

    UtilityMeterMapper INSTANCE = Mappers.getMapper(UtilityMeterMapper.class);

    List<UtilityMeterModel> entitiesToListUtilityMeterModel(List<UtilityMeterEntity> utilityMeterEntities);

}
