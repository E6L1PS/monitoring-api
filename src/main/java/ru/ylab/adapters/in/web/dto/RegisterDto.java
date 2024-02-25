package ru.ylab.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO модель класса User
 *
 * @author Pesternikov Danil
 */
public record RegisterDto(
        @JsonProperty("username")
        String username,

        @JsonProperty("password")
        String password
) {
}
