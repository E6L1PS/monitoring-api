package ru.ylab.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Класс, представляющий утилитарный счётчик.
 *
 * <p>Данный класс использует аннотации Lombok для автоматической генерации
 * кода. Он содержит поля для хранения текущих показаний счётчика,
 * типа счётчика и даты последних показаний.</p>
 *
 * @author Pesternikov Danil
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilityMeter {

    /**
     * Текущие показания счётчика.
     */
    private Double counter;

    /**
     * Тип счётчика.
     */
    private MeterType meterType;


    /**
     * Дата последних показаний счётчика.
     */
    private LocalDate readingsDate;
}
