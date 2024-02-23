package ru.ylab.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Создан: 20.02.2024.
 *
 * @author Pesternikov Danil
 */
@Slf4j
//@EnableTransactionManagement
@Configuration
public class JdbcConfig {

    @Bean
    public DataSource dataSource(@Value("${jdbc.url}") String jdbcUrl,
                                 @Value("${jdbc.username}") String jdbcUsername,
                                 @Value("${jdbc.password}") String jdbcPassword) {
        HikariConfig config = new HikariConfig();
        log.info(jdbcUrl);
        log.info(jdbcUsername);
        log.info(jdbcPassword);
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUsername);
        config.setPassword(jdbcPassword);
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
