package ru.ylab.adapters.out.persistence.repository;

import org.junit.jupiter.api.*;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.application.out.AuditRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@DisplayName("Интеграционный тест для AuditRepository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuditRepositoryIntegrationTest extends BaseIntegrationTest {

    AuditRepository auditRepository = new AuditRepositoryImpl(jdbcTemplate);

    @Test
    @Order(1)
    @DisplayName("Чтение аудитов после миграции - должен быть пустым")
    void testFindAllAfterMigration() {
        List<AuditEntity> auditEntities = auditRepository.findAll();

        assertThat(auditEntities).isEmpty();
    }

    @Test
    @Order(2)
    @DisplayName("Сохранение аудита - должен иметь размер 1 после сохранения")
    void testSaveAndFindAll() {
        auditRepository.save(AuditEntity.builder()
                .userId(1L)
                .info("info")
                .dateTime(LocalDateTime.now())
                .build());
        List<AuditEntity> newAuditEntities = auditRepository.findAll();

        assertThat(newAuditEntities).hasSize(1);
    }
}
