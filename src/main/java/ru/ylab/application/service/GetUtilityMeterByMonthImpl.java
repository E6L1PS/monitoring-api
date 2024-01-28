package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetUtilityMeterByMonth;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.model.UtilityMeterModel;
import ru.ylab.application.out.MeterRepository;

import java.util.List;

@Singleton
public class GetUtilityMeterByMonthImpl implements GetUtilityMeterByMonth {

    @Autowired
    MeterRepository meterRepository;

    @Override
    public List<UtilityMeterModel> execute(Integer month) {
        return UtilityMeterMapper.INSTANCE.entitiesToListUtilityMeterModel(meterRepository.findByMonth(month));
    }
}
