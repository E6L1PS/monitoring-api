package ru.ylab.application.model;

import ru.ylab.domain.model.MeterType;

import java.time.LocalDate;

public record UtilityMeterModel(String username, Double counter, MeterType meterType, LocalDate readingsDate) {

    @Override
    public String toString() {
        return "Тип счетчика: " + meterType.getName() +
                " - Показатель: " + counter +
                " - Дата подачи: " + readingsDate;
    }
}
