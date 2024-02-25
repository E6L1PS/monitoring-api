package ru.ylab.infrastructure.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Создан: 11.02.2024.
 *
 * @author Pesternikov Danil
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggableAspect {

    @Pointcut("@within(ru.ylab.infrastructure.aspect.annotation.Loggable) && execution(* *(..))")
    public void annotatedByLoggable() {
    }

    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Called method " + proceedingJoinPoint.getSignature());
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info("Execution of method " + proceedingJoinPoint.getSignature() + " finished. Time is " + (end - start) + " ms");
        return result;
    }
}
