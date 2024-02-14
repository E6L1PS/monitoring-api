package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.MeterType;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Singleton
public class AddNewMeterTypeImpl implements AddNewMeterType {

    @Autowired
    private MeterTypeRepository meterTypeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(MeterType meterType) {
        MeterTypeEntity meterTypeEntity = MeterTypeMapper.INSTANCE.toEntity(meterType);
        meterTypeRepository.save(meterTypeEntity);
    }
}
