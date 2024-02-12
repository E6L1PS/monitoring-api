package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetAllUtilityMeter;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.model.UtilityMeterModel;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.UserRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;

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

    @Autowired
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterModel> execute() {
        return UtilityMeterMapper.INSTANCE.entitiesToListUtilityMeterModel(meterRepository.findAll());
    }
}
