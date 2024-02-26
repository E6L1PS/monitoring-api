package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.in.GetAllUtilityMeterById;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Transactional
@Service
@RequiredArgsConstructor
public class GetAllUtilityMeterByIdImpl implements GetAllUtilityMeterById {

    private final MeterRepository meterRepository;

    private final UtilityMeterMapper utilityMeterMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterDto> execute(Long userId) {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findAllByUserId(userId);
        List<UtilityMeter> utilityMeters = utilityMeterMapper.toListDomain(utilityMeterEntities);
        //internal business logic with domain model if needed
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }
}
