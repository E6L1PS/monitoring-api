package ru.ylab.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * Создан: 24.02.2024.
 *
 * @author Pesternikov Danil
 */
public record TokenDto(
        String subject,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        Date issuedDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        Date expirationTime,
        List<String> roles
) {

}