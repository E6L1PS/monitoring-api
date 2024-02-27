package ru.ylab.domain.model;

import lombok.*;

/**
 * Класс, представляющий тип счётчика.
 *
 * <p>Данный класс использует аннотации Lombok для автоматической генерации
 * кода. Он содержит поле для хранения имени типа счётчика.</p>
 *
 * @author Pesternikov Danil
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MeterType {

    /**
     * имя типа счётчика.
     */
    private String name;
}
