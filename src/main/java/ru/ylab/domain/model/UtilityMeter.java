package ru.ylab.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Класс, представляющий утилитарный счетчик.
 * Каждый объект этого класса содержит информацию о показаниях счетчика,
 * его типе и дате считывания.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilityMeter {

    /** Показания счетчика.*/
    private Double counter;

    /** Тип счетчика, определенный объектом класса MeterType.*/
    private MeterType meterType;

    /**  Дата считывания показаний счетчика.*/
    private LocalDate readingsDate;
}
