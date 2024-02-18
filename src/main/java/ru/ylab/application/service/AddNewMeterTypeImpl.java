package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
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
@RequiredArgsConstructor
@Service
public class AddNewMeterTypeImpl implements AddNewMeterType {


    private final MeterTypeRepository meterTypeRepository;

    private final MeterTypeMapper meterTypeMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(MeterType meterType) {
        MeterTypeEntity meterTypeEntity = meterTypeMapper.toEntity(meterType);
        meterTypeRepository.save(meterTypeEntity);
    }
}
