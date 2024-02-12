package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetUtilityMeterByMonth;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.adapters.in.web.dto.UtilityMeterModel;
import ru.ylab.application.out.MeterRepository;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Singleton
public class GetUtilityMeterByMonthImpl implements GetUtilityMeterByMonth {

    @Autowired
    private MeterRepository meterRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterModel> execute(Integer month, Long userId) {
        return UtilityMeterMapper.INSTANCE.entitiesToListUtilityMeterModel(
                meterRepository.findByMonthAndUserId(month, userId)
        );
    }
}
