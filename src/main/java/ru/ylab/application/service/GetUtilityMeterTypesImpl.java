package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.application.in.GetUtilityMeterTypes;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.infrastructure.aspect.annotation.Auditable;
import ru.ylab.infrastructure.aspect.annotation.Loggable;
import ru.ylab.domain.model.MeterType;

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
public class GetUtilityMeterTypesImpl implements GetUtilityMeterTypes {

    private final MeterTypeRepository meterTypeRepository;

    private final MeterTypeMapper meterTypeMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeterType> execute() {
        List<MeterTypeEntity> meterTypeEntities = meterTypeRepository.findAll();
        return meterTypeMapper.toListDomain(meterTypeEntities);
    }
}
