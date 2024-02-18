package ru.ylab.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.application.out.AuditRepository;

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

    @Pointcut("@within(ru.ylab.aspect.annotation.Auditable) && execution(* *(..))")
    public void annotatedByAuditable() {
    }

    @After("annotatedByAuditable()")
    public void auditing(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        var info = generateInfoMessage(className);

        log.info("Start saving audit for " + className);


        Object[] args = joinPoint.getArgs();
        var userId = 1L;
        for (Object arg : args) {
            if (arg instanceof Long id) {
                userId = id;
                break;
            }
        }
        auditRepository.save(AuditEntity.builder()
                .info(info)
                .dateTime(LocalDateTime.now())
                .userId(userId) //TODO Придумать как получать userId
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
            default -> "Действие";
        };
    }
}
