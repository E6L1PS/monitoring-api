package ru.ylab.adapters.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
