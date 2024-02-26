package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.in.GetUtilityMeterByMonth;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Loggable
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetUtilityMeterByMonthImpl implements GetUtilityMeterByMonth {

    private final MeterRepository meterRepository;

    private final UtilityMeterMapper utilityMeterMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterDto> execute(Integer month, Long userId) {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findByMonthAndUserId(month, userId);
        List<UtilityMeter> utilityMeters = utilityMeterMapper.toListDomain(utilityMeterEntities);
        //internal business logic with domain model if needed
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }
}
