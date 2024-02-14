package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetAllUtilityMeter;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Singleton
public class GetAllUtilityMeterImpl implements GetAllUtilityMeter {

    @Autowired
    private MeterRepository meterRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeter> execute() {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findAll();
        return UtilityMeterMapper.INSTANCE.toListDomain(utilityMeterEntities);
    }
}
