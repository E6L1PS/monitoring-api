package ru.ylab.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.ylab.adapters.in.web.listener.ApplicationContextInitializationListener;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.adapters.out.persistence.repository.AuditRepositoryImpl;
import ru.ylab.adapters.out.persistence.repository.UserRepositoryImpl;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.UserRepository;

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
        UserRepository userRepository;
        try {
            userRepository = ApplicationContextInitializationListener.context.getObject(UserRepositoryImpl.class);
            auditRepository = ApplicationContextInitializationListener.context.getObject(AuditRepositoryImpl.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String className = joinPoint.getTarget().getClass().getName();
        //var info = generateInfoMessage(joinPoint.getTarget().getClass());
        auditRepository.save(AuditEntity.builder()
                .info(className)
                .dateTime(LocalDateTime.now())
                .userId(1L) //TODO Придумать как получать userId
                .build());

        System.out.println("-----------audit save --------");
    }
//
//    private String generateInfoMessage(Class<?> aClass) {}


}
