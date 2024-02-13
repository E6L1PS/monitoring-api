package ru.ylab.adapters.out.persistence.repository;

import org.junit.jupiter.api.*;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.application.out.MeterTypeRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@DisplayName("Интеграционный тест для MeterTypeRepository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeterTypeRepositoryIntegrationTest extends BaseIntegrationTest {
    MeterTypeRepository meterTypeRepository = new MeterTypeRepositoryImpl(connectionManager);

    @Test
    @Order(1)
    @DisplayName("Чтение типов после миграции - должен иметь размер 3")
    void testFindAllAfterMigration() {
        var types = meterTypeRepository.findAll();

        assertThat(types.get(0).getName()).hasToString("Холодная вода");
        assertThat(types.get(1).getName()).hasToString("Горячая вода");
        assertThat(types.get(2).getName()).hasToString("Отопление");
        assertThat(types).hasSize(3);
    }

    @Test
    @Order(2)
    @DisplayName("Сохранение нового типа счетчика - должен иметь размер 4 после сохранения")
    void testSaveAndFindAll() {
        meterTypeRepository.save(MeterTypeEntity.builder().name("New Type").build());
        var types = meterTypeRepository.findAll();

        assertThat(types).hasSize(4);
    }

    @Test
    @Order(3)
    @DisplayName("Тип существует")
    void testIsMeterTypeExists() {
        var isExist = meterTypeRepository.isMeterTypeExists("New Type");

        assertThat(isExist).isTrue();
    }

    @Test
    @Order(4)
    @DisplayName("Тип не существует")
    void testIsNotMeterTypeExists() {
        var isExist = meterTypeRepository.isMeterTypeExists("dhdhyktjr");

        assertThat(isExist).isFalse();
    }
}
