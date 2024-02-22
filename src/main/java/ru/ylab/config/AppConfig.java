package ru.ylab.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Создан: 19.02.2024.
 *
 * @author Pesternikov Danil
 */
@EnableAspectJAutoProxy
@Configuration
@ComponentScan(basePackages = "ru.ylab")
public class AppConfig {
}
