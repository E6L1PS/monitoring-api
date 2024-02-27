package ru.ylab.adapters.out.persistence.entity;

import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Представляет сущность аудита для бд, которая хранит информацию о действии пользователя.
 *
 * @author Pesternikov Danil
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuditEntity {

    /**
     * Уникальный идентификатор этой сущности аудита.
     */
    private Long id;

    /**
     * Уникальный идентификатор пользователя, выполнившего действие.
     */
    @Nullable
    private Long userId;

    /**
     * Дополнительная информация о выполняемом пользователем действии.
     */
    private String info;

    /**
     * Дата и время выполнения действия.
     */
    private LocalDateTime dateTime;

    /**
     * Возвращает строковое представление этой сущности аудита.
     *
     * @return строковое представление этой сущности аудита.
     */
    @Override
    public String toString() {
        return "[DateTime: " + dateTime +
               "; UserId: " + userId +
               "; Info: " + info + ']';
    }
}
