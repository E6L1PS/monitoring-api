package ru.ylab.config;

import org.springframework.context.annotation.*;

/**
 * Создан: 19.02.2024.
 *
 * @author Pesternikov Danil
 */
@Import(WebConfig.class)
@EnableAspectJAutoProxy
@Configuration
@ComponentScan(basePackages = "ru.ylab")
public class AppConfig {
}
