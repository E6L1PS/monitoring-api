package ru.ylab.aspect.annotation;

import org.springframework.context.annotation.Import;
import ru.ylab.config.AuditingAutoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Создан: 26.02.2024.
 *
 * @author Pesternikov Danil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AuditingAutoConfiguration.class)
public @interface EnableAuditing {
}
