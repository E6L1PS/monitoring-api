package ru.ylab.adapters.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Представляет сущность аудита, которая хранит информацию о действии пользователя.
 *
 * @author Pesternikov Danil
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditEntity {

    /**
     * Уникальный идентификатор этой сущности аудита.
     */
    private Long id;

    /**
     * Уникальный идентификатор пользователя, выполнившего действие.
     */
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
