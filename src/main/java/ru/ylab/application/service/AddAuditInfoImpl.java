package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.application.in.AddAuditInfo;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.User;

import java.time.LocalDateTime;

/**
 * Создан: 26.02.2024.
 *
 * @author Pesternikov Danil
 */
@Loggable
@Transactional
@Service("AddAuditInfoImpl")
@RequiredArgsConstructor
public class AddAuditInfoImpl implements AddAuditInfo {

    private final AuditRepository auditRepository;

    @Override
    public void save(String className) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = null;

        if (principal instanceof User user) {
            userId = user.getId();
        }

        auditRepository.save(AuditEntity.builder()
                .info(generateInfoMessage(className))
                .dateTime(LocalDateTime.now())
                .userId(userId)
                .build());
    }

    private String generateInfoMessage(String className) {
        return switch (className) {
            case "AddNewMeterTypeImpl" -> "Добавлен новый тип счетчика!";
            case "GetAllUtilityMeterByIdImpl", "GetAllUtilityMeterImpl" -> "Просмотр всех показаний!";
            case "GetAuditInfoImpl" -> "Просмотр всех аудитов!";
            case "GetLastUtilityMeterImpl" -> "Просмотр последних показаний!";
            case "GetUtilityMeterByMonthImpl" -> "Просмотр всех показаний за конкретный месяц!";
            case "GetUtilityMeterTypesImpl" -> "Просмотр всех типов показаний!";
            case "LoginUserImpl" -> "Авторизация выполнена!";
            case "RegisterUserImpl" -> "Новый пользователь зарегистрирован!";
            case "SubmitUtilityMeterImpl" -> "Показания поданы!";
            default -> "Действие: " + className;
        };
    }

}
