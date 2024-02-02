package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetUtilityMeterByMonth;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.model.UtilityMeterModel;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class GetUtilityMeterByMonthImpl implements GetUtilityMeterByMonth {

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public List<UtilityMeterModel> execute(Integer month) {
        var userId = userRepository.getCurrentUserId();
        auditRepository.saveAudit(
                AuditEntity.builder()
                        .info("Получена история подачи показаний за " + month + "-й месяц")
                        .dateTime(LocalDateTime.now())
                        .userId(userId)
                        .build());
        return UtilityMeterMapper.INSTANCE.entitiesToListUtilityMeterModel(
                meterRepository.findByMonth(month, userId)
        );
    }
}
