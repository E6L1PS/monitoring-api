package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetAllUtilityMeterById;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.adapters.in.web.dto.UtilityMeterModel;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;

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
    public List<UtilityMeterModel> execute(Long userId) {
        return UtilityMeterMapper.INSTANCE.entitiesToListUtilityMeterModel(meterRepository.findAllByUserId(userId));
    }
}
