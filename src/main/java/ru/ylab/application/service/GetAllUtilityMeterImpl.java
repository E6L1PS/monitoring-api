package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.in.GetAllUtilityMeter;
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
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetAllUtilityMeterImpl implements GetAllUtilityMeter {

    private final MeterRepository meterRepository;

    private final UtilityMeterMapper utilityMeterMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeter> execute() {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findAll();
        return utilityMeterMapper.toListDomain(utilityMeterEntities);
    }
}
