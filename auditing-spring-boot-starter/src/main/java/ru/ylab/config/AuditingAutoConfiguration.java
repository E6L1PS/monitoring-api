package ru.ylab.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ylab.aspect.AuditableAspect;
import ru.ylab.aspect.annotation.EnableAuditing;

/**
 * Создан: 26.02.2024.
 *
 * @author Pesternikov Danil
 */
@Slf4j
@Configuration
@ConditionalOnBean(annotation = EnableAuditing.class)
public class AuditingAutoConfiguration {

    @Bean
    public AuditableAspect auditableAspect(ApplicationContext applicationContext) {
        return new AuditableAspect(applicationContext);
    }

    @PostConstruct
    public void init() {
        log.info("Initializing AuditingAutoConfiguration");
    }

}
