package ru.ylab.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.ylab.adapters.in.web.listener.ApplicationContextInitializationListener;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.adapters.out.persistence.repository.AuditRepositoryImpl;
import ru.ylab.application.out.AuditRepository;

import java.time.LocalDateTime;

/**
 * Создан: 11.02.2024.
 *
 * @author Pesternikov Danil
 */
@Aspect
public class AuditAspect {

    @Pointcut("@within(ru.ylab.aspect.annotation.Auditable) && execution(* *(..))")
    public void annotatedByAuditable() {
    }

    @After("annotatedByAuditable()")
    public void auditing(JoinPoint joinPoint) {
        AuditRepository auditRepository;
        try {
            auditRepository = ApplicationContextInitializationListener.context.getObject(AuditRepositoryImpl.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Object[] args = joinPoint.getArgs();
        var userId = 1L;
        for (Object arg : args) {
            if (arg instanceof Long id) {
                userId = id;
                System.out.println("Найден аргумент id: " + id);
                break;
            }
        }
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println(className);
        var info = generateInfoMessage(className);
        auditRepository.save(AuditEntity.builder()
                .info(info)
                .dateTime(LocalDateTime.now())
                .userId(userId) //TODO Придумать как получать userId
                .build());

        System.out.println("-----------audit save --------");
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
