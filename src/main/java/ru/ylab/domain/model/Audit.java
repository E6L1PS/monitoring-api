package ru.ylab.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Класс, представляющий информацию об аудите.
 *
 * <p>Данный класс использует аннотации Lombok для автоматической генерации
 * кода. Он содержит поля для хранения информации, имени пользователя и
 * времени события аудита.</p>
 *
 * @author Pesternikov Danil
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Audit {

    /**
     * Уникальный идентификатор этой сущности аудита.
     */
    private Long id;

    /**
     * Уникальный идентификатор пользователя, выполнившего действие.
     */
    private Long userId;

    /**
     * информация об аудите.
     */
    private String info;

    /**
     * Время события аудита.
     */
    private LocalDateTime dateTime;
}
