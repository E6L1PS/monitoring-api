package ru.ylab.adapters.out.persistence.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@Testcontainers
public abstract class BaseIntegrationTest {

    private static final String DB_NAME = "TEST";
    private static final String USERNAME = "TEST";
    private static final String PASSWORD = "TEST";

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.1")
                    .withDatabaseName(DB_NAME)
                    .withUsername(USERNAME)
                    .withPassword(PASSWORD)
                    .withInitScript("init-schema.sql");

    protected static JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void setupConnectionAndMigration() {
        postgres.start();

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(postgres.getJdbcUrl());
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        DataSource hikariDataSource = new HikariDataSource(config);
        liquibaseUpdate(hikariDataSource);
        jdbcTemplate = new JdbcTemplate(hikariDataSource);
    }

    private static void liquibaseUpdate(DataSource dataSource) {
        try (var connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName("monitoring_schema");
            database.setLiquibaseSchemaName("migration_schema");
            Liquibase liquibase = new Liquibase(
                    "db.changelog/db.changelog-master.xml",
                    new ClassLoaderResourceAccessor(),
                    database);

            liquibase.update();
        } catch (LiquibaseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
