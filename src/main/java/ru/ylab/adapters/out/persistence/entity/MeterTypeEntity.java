package ru.ylab.adapters.out.persistence.entity;

import lombok.*;

/**
 * Представляет сущность типа счетчика для бд.
 *
 * @author Pesternikov Danil
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MeterTypeEntity {

    /**
     * Наименование типа счетчика.
     */
    private String name;
}
