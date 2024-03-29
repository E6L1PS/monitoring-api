package ru.ylab.adapters.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Представляет сущность счетчика коммунальных услуг для бд.
 *
 * @author Pesternikov Danil
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
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
