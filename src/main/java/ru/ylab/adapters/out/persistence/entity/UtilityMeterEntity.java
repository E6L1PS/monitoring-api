package ru.ylab.adapters.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ylab.domain.model.MeterType;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilityMeterEntity {

    private String username;
    /** Показания счетчика.*/
    private Double counter;

    /** Тип счетчика, определенный объектом класса MeterType.*/
    private MeterType meterType;

    /**  Дата считывания показаний счетчика.*/
    private LocalDate readingsDate;
}
