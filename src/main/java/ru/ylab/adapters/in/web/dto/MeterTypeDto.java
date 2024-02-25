package ru.ylab.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO модель класса MeterType
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
public record MeterTypeDto(@JsonProperty("name") String name) {
}
