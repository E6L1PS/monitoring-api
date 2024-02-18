package ru.ylab.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;

@Mapper
public interface UtilityMeterMapper {

    UtilityMeterMapper INSTANCE = Mappers.getMapper(UtilityMeterMapper.class);

    List<UtilityMeterDto> toListDto(List<UtilityMeter> utilityMeters);

    List<UtilityMeter> toListDomain(List<UtilityMeterEntity> utilityMeterEntities);

}
