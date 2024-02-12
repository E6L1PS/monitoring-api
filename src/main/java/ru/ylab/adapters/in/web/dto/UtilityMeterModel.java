package ru.ylab.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record UtilityMeterModel(
        Long userId,
        Double counter,
        String type,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate readingsDate
) {
}
