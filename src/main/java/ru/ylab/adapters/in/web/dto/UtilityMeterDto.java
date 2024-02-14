package ru.ylab.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * DTO модель класса UtilityMeter
 * @author Pesternikov Danil
 */
public record UtilityMeterDto(
        Long userId,
        Double counter,
        String type,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate readingsDate
) {
}
