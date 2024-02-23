package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.in.GetAllUtilityMeterById;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.infrastructure.aspect.annotation.Auditable;
import ru.ylab.infrastructure.aspect.annotation.Loggable;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetAllUtilityMeterByIdImpl implements GetAllUtilityMeterById {

    private final MeterRepository meterRepository;

    private final UtilityMeterMapper utilityMeterMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeter> execute(Long userId) {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findAllByUserId(userId);
        return utilityMeterMapper.toListDomain(utilityMeterEntities);
    }
}
