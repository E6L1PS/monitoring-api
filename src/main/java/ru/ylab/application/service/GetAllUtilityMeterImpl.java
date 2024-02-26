package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.in.GetAllUtilityMeter;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.domain.model.UtilityMeter;
import ru.ylab.aspect.annotation.Loggable;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Transactional
@Service
@RequiredArgsConstructor
public class GetAllUtilityMeterImpl implements GetAllUtilityMeter {

    private final MeterRepository meterRepository;

    private final UtilityMeterMapper utilityMeterMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterDto> execute() {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findAll();
        List<UtilityMeter> utilityMeters = utilityMeterMapper.toListDomain(utilityMeterEntities);
        //internal business logic with domain model if needed
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }
}
