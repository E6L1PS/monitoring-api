package ru.ylab.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Создан: 11.02.2024.
 *
 * @author Pesternikov Danil
 */
@Aspect
public class LoggableAspect {
    @Pointcut("@within(ru.ylab.aspect.Loggable) && execution(* *(..))")
    public void annotatedByLoggable() {
    }

    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Called method " + proceedingJoinPoint.getSignature());
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("Execution of method " + proceedingJoinPoint.getSignature() + " finished. Time is " + (end - start));
        return result;
    }
}
