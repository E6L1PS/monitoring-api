package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetUtilityMeterTypes;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.domain.model.MeterType;

import java.util.List;

@Singleton
public class GetUtilityMeterTypesImpl implements GetUtilityMeterTypes {

    @Autowired
    private MeterTypeRepository meterTypeRepository;

    @Override
    public List<MeterType> execute() {
        var types = meterTypeRepository.findAll();
        return MeterTypeMapper.INSTANCE.entitiesToListMeterType(types);
    }
}
