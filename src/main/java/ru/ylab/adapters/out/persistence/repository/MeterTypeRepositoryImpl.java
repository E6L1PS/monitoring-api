package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.annotations.Init;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.out.MeterTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Singleton
@NoArgsConstructor
public class MeterTypeRepositoryImpl implements MeterTypeRepository {

    private final List<MeterTypeEntity> meterTypes = new ArrayList<>();

    @Init
    public void init() {
        meterTypes.add(MeterTypeEntity.builder().name("Отопление").build());
        meterTypes.add(MeterTypeEntity.builder().name("Горячая вода").build());
        meterTypes.add(MeterTypeEntity.builder().name("Холодная вода").build());
    }

    @Override
    public List<MeterTypeEntity> findAll() {
        return meterTypes;
    }

    @Override
    public MeterTypeEntity createType(String typeName) {
        MeterTypeEntity meterTypeEntity = MeterTypeEntity.builder().name(typeName).build();
        meterTypes.add(meterTypeEntity);
        return meterTypeEntity;
    }

    @Override
    public Boolean isValid(MeterTypeEntity meterTypeEntity) {
        return meterTypes.contains(meterTypeEntity);
    }
}
