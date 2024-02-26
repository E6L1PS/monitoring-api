package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.exception.MonthlySubmitLimitException;
import ru.ylab.application.exception.NotValidMeterTypeException;
import ru.ylab.application.in.SubmitUtilityMeter;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Transactional
@Service
@RequiredArgsConstructor
public class SubmitUtilityMeterImpl implements SubmitUtilityMeter {

    private final MeterRepository meterRepository;

    private final MeterTypeRepository meterTypeRepository;

    /**
     * {@inheritDoc}
     *
     * @throws NotValidMeterTypeException  в случае если такого типа счетчика не существует
     * @throws MonthlySubmitLimitException в случае если пользователь уже подавал показания в текущем месяце
     */
    @Override
    public void execute(Map<String, Double> mapUtilityMeters, Long userId) {

        if (meterRepository.isSubmitted(userId)) {
            throw new MonthlySubmitLimitException("В этом месяце вы уже подали!");
        }

        if (!isValid(mapUtilityMeters.keySet())) {
            throw new NotValidMeterTypeException("Такой тип не существует!");
        }

        List<UtilityMeterEntity> utilityMeterEntities = mapUtilityMeters.entrySet().stream()
                .map(entry -> UtilityMeterEntity.builder()
                        .userId(userId)
                        .type(entry.getKey())
                        .counter(entry.getValue())
                        .readingsDate(LocalDate.now())
                        .build())
                .toList();

        meterRepository.saveAll(utilityMeterEntities);
    }

    private Boolean isValid(Set<String> inputMeters) {
        List<String> meterTypes = meterTypeRepository.findAll().stream()
                .map(MeterTypeEntity::getName)
                .toList();
        return inputMeters.containsAll(meterTypes);
    }
}
