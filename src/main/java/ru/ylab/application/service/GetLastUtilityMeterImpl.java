package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.in.GetLastUtilityMeter;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.infrastructure.aspect.annotation.Auditable;
import ru.ylab.infrastructure.aspect.annotation.Loggable;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetLastUtilityMeterImpl implements GetLastUtilityMeter {

    private final MeterRepository meterRepository;

    private final UtilityMeterMapper utilityMeterMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterDto> execute(Long userId) {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findLastByUserId(userId);
        List<UtilityMeter> utilityMeters = utilityMeterMapper.toListDomain(utilityMeterEntities);
        //internal business logic with domain model if needed
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }
}
