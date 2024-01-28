package ru.ylab.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий тип счётчика.
 *
 * <p>Данный класс использует аннотации Lombok для автоматической генерации
 * кода. Он содержит поле для хранения имени типа счётчика.</p>
 *
 * @author Pesternikov Danil
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeterType {

    /**
     * имя типа счётчика.
     */
    private String name;
}
