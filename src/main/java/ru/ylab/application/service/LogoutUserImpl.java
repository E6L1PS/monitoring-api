package ru.ylab.application.service;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.LogoutUser;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.UserRepository;

import java.time.LocalDateTime;

@Singleton
@NoArgsConstructor
public class LogoutUserImpl implements LogoutUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public void execute() {
        var userId = userRepository.getCurrentUserId();
        userRepository.logout();
        auditRepository.saveAudit(
                AuditEntity.builder()
                        .info("Завершение работы")
                        .dateTime(LocalDateTime.now())
                        .userId(userId)
                        .build());
    }
}
