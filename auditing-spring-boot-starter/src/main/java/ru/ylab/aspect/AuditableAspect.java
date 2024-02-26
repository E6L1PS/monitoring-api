package ru.ylab.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * Создан: 11.02.2024.
 *
 * @author Pesternikov Danil
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class AuditableAspect {

    private final ApplicationContext applicationContext;

    @Pointcut("@within(ru.ylab.aspect.annotation.Auditable) && execution(* *(..))")
    public void annotatedByAuditable() {
    }

    @After("annotatedByAuditable()")
    public void auditing(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("Start saving audit for " + className);
        Object auditRepositoryImpl = applicationContext.getBean("AddAuditInfoImpl");
        try {
            Class<?> beanClass = auditRepositoryImpl.getClass();
            Method saveMethod = beanClass.getMethod("save", String.class);
            saveMethod.invoke(auditRepositoryImpl, className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Audit saved");
    }

}
