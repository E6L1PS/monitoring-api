package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetUtilityMeterTypes;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.MeterType;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Singleton
public class GetUtilityMeterTypesImpl implements GetUtilityMeterTypes {

    @Autowired
    private MeterTypeRepository meterTypeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeterType> execute() {
        return MeterTypeMapper.INSTANCE.entitiesToListMeterType(meterTypeRepository.findAll());
    }
}
