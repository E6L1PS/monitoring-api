package ru.ylab.adapters.out.persistence.repository;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ylab.adapters.util.ConnectionManager;

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
    private static final int DEFAULT_POOL_SIZE = 10;

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.1")
                    .withDatabaseName(DB_NAME)
                    .withUsername(USERNAME)
                    .withPassword(PASSWORD)
                    .withInitScript("init-schema.sql");

    protected static ConnectionManager connectionManager;

    @BeforeAll
    static void setupConnectionAndMigration() {
        postgres.start();
        connectionManager = new ConnectionManager(postgres.getJdbcUrl(), USERNAME, PASSWORD, DEFAULT_POOL_SIZE);
        liquibaseUpdate();
    }

    private static void liquibaseUpdate() {
        try (var connection = connectionManager.get()) {
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
