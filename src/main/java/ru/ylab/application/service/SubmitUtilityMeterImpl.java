package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.exception.MonthlySubmitLimitException;
import ru.ylab.application.exception.NotValidMeterTypeException;
import ru.ylab.application.in.SubmitUtilityMeter;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.infrastructure.aspect.annotation.Auditable;
import ru.ylab.infrastructure.aspect.annotation.Loggable;
import ru.ylab.domain.model.UtilityMeter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@RequiredArgsConstructor
@Transactional
@Service
public class SubmitUtilityMeterImpl implements SubmitUtilityMeter {

    private final MeterRepository meterRepository;

    private final MeterTypeRepository meterTypeRepository;

    private final UtilityMeterMapper utilityMeterMapper;

    /**
     * {@inheritDoc}
     *
     * @throws NotValidMeterTypeException  в случае если такого типа счетчика не существует
     * @throws MonthlySubmitLimitException в случае если пользователь уже подавал показания в текущем месяце
     */
    @Override
    public List<UtilityMeter> execute(Map<String, Double> utilityMeters, Long userId) {
        List<UtilityMeterEntity> utilityMeterEntityList = new ArrayList<>();

        if (!meterRepository.isSubmitted(userId)) {
            utilityMeters.forEach((type, counter) -> {
                if (meterTypeRepository.isMeterTypeExists(type)) {
                    UtilityMeterEntity utilityMeterEntity = meterRepository.save(
                            UtilityMeterEntity.builder()
                                    .userId(userId)
                                    .type(type)
                                    .counter(counter)
                                    .readingsDate(LocalDate.now())
                                    .build()
                    );
                    utilityMeterEntityList.add(utilityMeterEntity);
                } else {
                    throw new NotValidMeterTypeException("Такой тип не существует!");
                }
            });

            return utilityMeterMapper.toListDomain(utilityMeterEntityList);
        } else {
            throw new MonthlySubmitLimitException("В этом месяце вы уже подали!");
        }
    }
}
