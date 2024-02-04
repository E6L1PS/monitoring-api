package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.exception.MonthlySubmitLimitException;
import ru.ylab.application.exception.NotValidMeterTypeException;
import ru.ylab.application.in.SubmitUtilityMeter;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.application.out.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Singleton
public class SubmitUtilityMeterImpl implements SubmitUtilityMeter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private MeterTypeRepository meterTypeRepository;

    @Override
    public void execute(Map<String, Double> utilityMeters) {
        var userId = userRepository.getCurrentUserId();
        if (!meterRepository.isSubmitted(userId)) {
            utilityMeters.forEach((type, counter) -> {
                if (meterTypeRepository.isMeterTypeExists(type)) {
                    meterRepository.save(
                            UtilityMeterEntity.builder()
                                    .userId(userId)
                                    .type(type)
                                    .counter(counter)
                                    .readingsDate(LocalDate.now())
                                    .build()
                    );

                    auditRepository.save(
                            AuditEntity.builder()
                                    .info("Показания поданы")
                                    .dateTime(LocalDateTime.now())
                                    .userId(userId)
                                    .build());
                } else {
                    throw new NotValidMeterTypeException("Not valid type!");
                }
            });
        } else {
            throw new MonthlySubmitLimitException("В этом месяце вы уже подали!");
        }
    }
}
