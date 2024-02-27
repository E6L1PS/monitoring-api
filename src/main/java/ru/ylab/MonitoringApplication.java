package ru.ylab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ylab.aspect.annotation.EnableAuditing;

/**
 * Создан: 26.02.2024.
 *
 * @author Pesternikov Danil
 */
@EnableAuditing
@SpringBootApplication
public class MonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringApplication.class, args);
    }
}
