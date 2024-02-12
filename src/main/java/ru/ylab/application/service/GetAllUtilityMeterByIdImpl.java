package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.application.in.GetAllUtilityMeterById;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.model.UtilityMeterModel;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.UserRepository;

import java.util.List;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
public class GetAllUtilityMeterByIdImpl implements GetAllUtilityMeterById {

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UtilityMeterModel> execute(Long userId) {
        return UtilityMeterMapper.INSTANCE.entitiesToListUtilityMeterModel(meterRepository.findAllByUserId(userId));
    }
}
