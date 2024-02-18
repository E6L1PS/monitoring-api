package ru.ylab.adapters.out.persistence.repository;

import org.junit.jupiter.api.*;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.out.MeterRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@DisplayName("Интеграционный тест для MeterRepository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeterRepositoryIntegrationTest extends BaseIntegrationTest {

    MeterRepository meterRepository = new MeterRepositoryImpl(connectionManager);

    @Test
    @Order(1)
    @DisplayName("Чтение счетчиков после миграции - должен быть пустым")
    void testFindAllAfterMigration() {
        var meters = meterRepository.findAll();

        assertThat(meters).isEmpty();
    }

    @Test
    @Order(2)
    @DisplayName("Сохранение 4-х счетчиков - должен иметь размер 4 после сохранения")
    void testSave() {
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

        var meters = meterRepository.findAll();

        assertThat(meters).hasSize(4);
    }

    @Test
    @Order(3)
    @DisplayName("Чтение счетчиков по id пользователя - должен иметь размер 1")
    void testFindAllByUserId() {
        var meters = meterRepository.findAllByUserId(1L);

        assertThat(meters).hasSize(1);
    }

    @Test
    @Order(4)
    @DisplayName("Чтение последних показаний по id пользователя - должен иметь размер 2")
    void testFindLastByUserId() {
        var meters = meterRepository.findLastByUserId(2L);

        assertThat(meters).hasSize(2);
    }

    @Test
    @Order(4)
    @DisplayName("Чтение счетчиков по месяцу и id пользователя - должен иметь размер 1")
    void testFindByMonthAndUserId() {
        var meters = meterRepository.findByMonthAndUserId(1, 2L);

        assertThat(meters).hasSize(1);
    }

    @Test
    @Order(5)
    @DisplayName("Показания были отправлены в текущем месяце")
    void testIsSubmitted() {
        var isSubmitted = meterRepository.isSubmitted(2L);

        assertThat(isSubmitted).isTrue();
    }

    @Test
    @Order(5)
    @DisplayName("Показания не были отправлены в текущем месяце")
    void testIsNotSubmitted() {
        var isSubmitted = meterRepository.isSubmitted(1L);

        assertThat(isSubmitted).isFalse();
    }

}
