package ru.ylab.infrastructure.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.domain.model.User;

import java.time.LocalDateTime;

/**
 * Создан: 11.02.2024.
 *
 * @author Pesternikov Danil
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditRepository auditRepository;

    @Pointcut("@within(ru.ylab.infrastructure.aspect.annotation.Auditable) && execution(* *(..))")
    public void annotatedByAuditable() {
    }

    @AfterReturning(pointcut = "annotatedByAuditable()", returning = "returnValue")
    public void auditing(JoinPoint joinPoint, Object returnValue) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        User user = User.builder().build();
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            if (className.equals("RegisterUserImpl")) {
                if (returnValue instanceof Long id) {
                    user.setId(id);
                }
            } else {
                user.setId(1L);
            }
        }

        var info = generateInfoMessage(className);
        log.info("Start saving audit for " + className + "; userId: " + user.getId().toString());
        auditRepository.save(AuditEntity.builder()
                .info(info)
                .dateTime(LocalDateTime.now())
                .userId(user.getId())
                .build());

        log.info("Audit saved");
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
