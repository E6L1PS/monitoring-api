package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.out.MeterRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
@NoArgsConstructor
public class MeterRepositoryImpl implements MeterRepository {

    private final List<UtilityMeterEntity> utilityMeterEntities = new ArrayList<>();

    @Override
    public List<UtilityMeterEntity> findAll() {
        return utilityMeterEntities;
    }

    @Override
    public List<UtilityMeterEntity> findAllByUsername(String username) {
        return utilityMeterEntities.stream()
                .filter(meter -> meter.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public List<UtilityMeterEntity> findLastByUsername(String username) {
        var optionalUtilityMeterEntity = utilityMeterEntities.stream()
                .min(Comparator.comparing(UtilityMeterEntity::getReadingsDate));
        if (optionalUtilityMeterEntity.isPresent()) {
            var date = optionalUtilityMeterEntity.get().getReadingsDate();
            return utilityMeterEntities.stream()
                    .filter(meter -> meter.getUsername().equals(username) && meter.getReadingsDate() == date)
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    @Override
    public List<UtilityMeterEntity> findByMonth(Integer month) {
        return utilityMeterEntities.stream()
                .filter(meter -> meter.getReadingsDate().getMonthValue() == month)
                .collect(Collectors.toList());
    }

    @Override
    public UtilityMeterEntity create(UtilityMeterEntity utilityMeterEntity) {
        utilityMeterEntities.add(utilityMeterEntity);
        return utilityMeterEntity;
    }
}
