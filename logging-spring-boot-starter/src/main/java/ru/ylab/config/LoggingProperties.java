package ru.ylab.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Создан: 26.02.2024.
 *
 * @author Pesternikov Danil
 */
@Slf4j
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "app.logging")
public class LoggingProperties {

    private boolean enabled = true;

}
