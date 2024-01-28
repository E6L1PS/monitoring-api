package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetUtilityMeter;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.model.UtilityMeterModel;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class GetUtilityMeterImpl implements GetUtilityMeter {

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public List<UtilityMeterModel> execute() {
        var username = userRepository.getCurrentUsername();
        auditRepository.saveAudit(AuditEntity.builder()
                .info("Получен актуальные показания счетчиков")
                .dateTime(LocalDateTime.now())
                .username(userRepository.getCurrentUsername())
                .build());
        return UtilityMeterMapper.INSTANCE.entitiesToListUtilityMeterModel(meterRepository.findLastByUsername(username));
    }
}