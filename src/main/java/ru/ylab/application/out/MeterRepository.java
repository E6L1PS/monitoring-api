package ru.ylab.application.out;

import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;

import java.util.List;

public interface MeterRepository {

    List<UtilityMeterEntity> findAll();

    List<UtilityMeterEntity> findAllByUsername(String username);

    List<UtilityMeterEntity> findLastByUsername(String username);

    List<UtilityMeterEntity> findByMonth(Integer month, String username);

    UtilityMeterEntity create(UtilityMeterEntity utilityMeterEntity);
}
