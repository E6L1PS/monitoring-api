package ru.ylab.adapters.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Представляет сущность утилитарного счетчика.
 *
 * @author Pesternikov Danil
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilityMeterEntity {

    /**
     * Уникальный идентификатор счетчика.
     */
    private Long id;

    /**
     * Уникальный идентификатор пользователя, которому принадлежит счетчик.
     */
    private Long userId;

    /**
     * Тип счетчика.
     */
    private String type;

    /**
     * Показания счетчика.
     */
    private Double counter;

    /**
     * Дата считывания показаний счетчика.
     */
    private LocalDate readingsDate;

    /**
     * Возвращает строковое представление этой сущности счетчика.
     *
     * @return строковое представление этой сущности счетчика.
     */
    @Override
    public String toString() {
        return "Тип счетчика: " + type +
               " - Показатель: " + counter +
               " - Дата подачи: " + readingsDate;
    }
}
