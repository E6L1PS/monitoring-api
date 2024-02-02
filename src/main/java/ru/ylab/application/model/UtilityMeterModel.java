package ru.ylab.application.model;

import java.time.LocalDate;

public record UtilityMeterModel(Long userId, Double counter, String type, LocalDate readingsDate) {

    @Override
    public String toString() {
        return "Тип счетчика: " + type +
               " - Показатель: " + counter +
               " - Дата подачи: " + readingsDate;
    }
}
