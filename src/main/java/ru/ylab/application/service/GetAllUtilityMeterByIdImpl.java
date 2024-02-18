package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
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
@Singleton
public class GetAllUtilityMeterByIdImpl implements GetAllUtilityMeterById {

    @Autowired
    private MeterRepository meterRepository;

    @Override
    public List<UtilityMeter> execute(Long userId) {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findAllByUserId(userId);
        return UtilityMeterMapper.INSTANCE.toListDomain(utilityMeterEntities);
    }
}
