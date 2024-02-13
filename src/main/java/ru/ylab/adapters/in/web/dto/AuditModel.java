package ru.ylab.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
public record AuditModel(
        Long id,
        Long userId,
        String info,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dateTime
) {
}
