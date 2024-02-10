package ru.ylab.adapters.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Представляет сущность типа счетчика для бд.
 *
 * @author Pesternikov Danil
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeterTypeEntity {

    /**
     * Наименование типа счетчика.
     */
    private String name;
}
