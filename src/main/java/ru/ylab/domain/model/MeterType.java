package ru.ylab.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий тип счетчика.
 * Каждый объект этого класса хранит информацию о имени типа счетчика.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeterType {

    /**
     * Название счетчика.
     */
    private String name;
}
