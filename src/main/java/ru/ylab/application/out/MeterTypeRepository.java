package ru.ylab.application.out;

import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;

import java.util.List;

public interface MeterTypeRepository {

    List<MeterTypeEntity> findAll();
    Boolean isValid(MeterTypeEntity meterTypeEntity);
    MeterTypeEntity createType(String typeName);
}
