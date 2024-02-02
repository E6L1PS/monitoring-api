package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetAllUtilityMeter;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.application.model.UtilityMeterModel;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class GetAllUtilityMeterImpl implements GetAllUtilityMeter {

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public List<UtilityMeterModel> execute() {
        var role = userRepository.getCurrentRoleUser();
        List<UtilityMeterEntity> entityList;
        if (role == Role.ADMIN) {
            entityList = meterRepository.findAll();
        } else {
            var userId = userRepository.getCurrentUserId();
            entityList = meterRepository.findAllByUserId(userId);
        }
        auditRepository.saveAudit(AuditEntity.builder()
                .info("Получена история подачи показаний")
                .dateTime(LocalDateTime.now())
                .userId(userRepository.getCurrentUserId())
                .build());
        return UtilityMeterMapper.INSTANCE.entitiesToListUtilityMeterModel(entityList);
    }
}
