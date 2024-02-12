package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.out.MeterTypeRepository;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Singleton
public class AddNewMeterTypeImpl implements AddNewMeterType {

    @Autowired
    private MeterTypeRepository meterTypeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String name) {
        meterTypeRepository.save(name);
    }
}
