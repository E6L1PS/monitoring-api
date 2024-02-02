package ru.ylab.adapters.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilityMeterEntity {

    private Long id;

    private Long userId;

    private String type;

    /** Показания счетчика.*/
    private Double counter;

    /**  Дата считывания показаний счетчика.*/
    private LocalDate readingsDate;
    @Override
    public String toString() {
        return "Тип счетчика: " + type +
               " - Показатель: " + counter +
               " - Дата подачи: " + readingsDate;
    }
}
