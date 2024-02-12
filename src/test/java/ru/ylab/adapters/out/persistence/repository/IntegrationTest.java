package ru.ylab.adapters.out.persistence.repository;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ylab.adapters.util.ConnectionManager.get;

@Testcontainers
class IntegrationTest {

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

    AuditRepository auditRepository = new AuditRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();
    MeterRepository meterRepository = new MeterRepositoryImpl();
    MeterTypeRepository meterTypeRepository = new MeterTypeRepositoryImpl();

    @BeforeAll
    static void setupConnectionAndMigration() {
        postgres.start();
        System.setProperty("POSTGRES_DB", DB_NAME);
        System.setProperty("POSTGRES_USER", USERNAME);
        System.setProperty("POSTGRES_PASSWORD", PASSWORD);
        System.setProperty("JDBC_URL", postgres.getJdbcUrl());
        liquibaseUpdate();
    }

    @Test
    void auditRepositoryTest() {
        List<AuditEntity> auditEntities = auditRepository.findAll();
        auditRepository.save(AuditEntity.builder()
                .userId(1L)
                .info("info")
                .dateTime(LocalDateTime.now())
                .build());
        List<AuditEntity> newAuditEntities = auditRepository.findAll();

        assertThat(auditEntities).isEmpty();
        assertThat(newAuditEntities).hasSize(1);
    }

    @Test
    void meterRepositoryTest() {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findAll();
        meterRepository.save(UtilityMeterEntity.builder()
                .userId(1L)
                .type("Горячая вода")
                .readingsDate(LocalDate.of(2023, 12, 1))
                .counter(100.0)
                .build());

        meterRepository.save(UtilityMeterEntity.builder()
                .userId(2L)
                .type("Горячая вода")
                .readingsDate(LocalDate.now())
                .counter(210.0)
                .build());

        meterRepository.save(UtilityMeterEntity.builder()
                .userId(2L)
                .type("Горячая вода")
                .readingsDate(LocalDate.now())
                .counter(220.0)
                .build());

        meterRepository.save(UtilityMeterEntity.builder()
                .userId(2L)
                .type("Горячая вода")
                .readingsDate(LocalDate.of(2024, 1, 1))
                .counter(230.0)
                .build());

        List<UtilityMeterEntity> newUtilityMeterEntities = meterRepository.findAll();
        List<UtilityMeterEntity> metersByUserId = meterRepository.findAllByUserId(1L);
        List<UtilityMeterEntity> metersLastByUserId = meterRepository.findLastByUserId(2L);
        List<UtilityMeterEntity> metersByMonthAndUserId = meterRepository.findByMonthAndUserId(1, 2L);

        Boolean submitted = meterRepository.isSubmitted(2L);
        Boolean notSubmitted = meterRepository.isSubmitted(1L);

        assertThat(utilityMeterEntities).isEmpty();
        assertThat(newUtilityMeterEntities).hasSize(4);
        assertThat(metersByUserId).hasSize(1);
        assertThat(metersLastByUserId).hasSize(2);
        assertThat(metersByMonthAndUserId).hasSize(1);

        assertThat(submitted).isTrue();
        assertThat(notSubmitted).isFalse();
    }

    @Test
    void meterTypeRepositoryTest() {
        List<MeterTypeEntity> defaultTypes = meterTypeRepository.findAll();

        meterTypeRepository.save("New Type");
        List<MeterTypeEntity> withNewType = meterTypeRepository.findAll();

        Boolean exist = meterTypeRepository.isMeterTypeExists("New Type");
        Boolean notExist = meterTypeRepository.isMeterTypeExists("dhdhyktjr");

        assertThat(defaultTypes).hasSize(3);
        assertThat(withNewType).hasSize(4);
        assertThat(exist).isTrue();
        assertThat(notExist).isFalse();
    }

    @Test
    void userRepositoryTest() {
        var expectedUser = UserEntity.builder()
                .id(1L)
                .username("admin")
                .password("admin")
                .role(Role.ADMIN)
                .build();

        var expectedNewUser = UserEntity.builder()
                .id(3L)
                .username("newuser")
                .password("newuser")
                .role(Role.USER)
                .build();

        var actualUser = userRepository.getByUsername("admin");
        userRepository.setupCurrentUser(actualUser);
        userRepository.save(expectedNewUser);
        var actualNewUser = userRepository.getByUsername("newuser");

        Role currentActualRoleUser = userRepository.getCurrentRoleUser();
        Long currentActualUserId = userRepository.getCurrentUserId();

        Boolean alreadyExists = userRepository.isAlreadyExists("newuser");
        Boolean notAlreadyExists = userRepository.isAlreadyExists("newuser1");

        assertThat(actualUser)
                .usingRecursiveComparison()
                .isEqualTo(expectedUser);
        assertThat(actualNewUser)
                .usingRecursiveComparison()
                .isEqualTo(expectedNewUser);

        assertThat(alreadyExists).isTrue();
        assertThat(notAlreadyExists).isFalse();

        assertThat(currentActualRoleUser).isEqualTo(Role.ADMIN);
        assertThat(currentActualUserId).isEqualTo(1L);
    }

    private static void liquibaseUpdate() {
        try (var connection = get()) {
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