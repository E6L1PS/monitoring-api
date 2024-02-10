package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.exception.UnauthorizedException;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;

import java.time.LocalDateTime;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Singleton
public class AddNewMeterTypeImpl implements AddNewMeterType {

    @Autowired
    private MeterTypeRepository meterTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    /**
     * {@inheritDoc}
     * @throws UnauthorizedException в случае если пользователь не обладает правами ADMIN
     */
    @Override
    public void execute(String name) {
        if (userRepository.getCurrentRoleUser() == Role.ADMIN) {
            meterTypeRepository.save(name);
            auditRepository.save(AuditEntity.builder()
                    .info("Добавлен новый тип счетчика: " + name)
                    .dateTime(LocalDateTime.now())
                    .userId(userRepository.getCurrentUserId())
                    .build());
        } else {
            throw new UnauthorizedException("Нету доступа для данного пользователя!");
        }
    }
}
