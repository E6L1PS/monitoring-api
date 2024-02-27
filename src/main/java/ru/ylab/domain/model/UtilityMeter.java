package ru.ylab.domain.model;

import lombok.*;

import java.time.LocalDate;

/**
 * Класс, представляющий счетчика коммунальных услуг.
 *
 * <p>Данный класс использует аннотации Lombok для автоматической генерации
 * кода. Он содержит поля для хранения текущих показаний счётчика,
 * типа счётчика и даты считывания показаний счетчика.</p>
 *
 * @author Pesternikov Danil
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UtilityMeter {

    /**
     * Уникальный идентификатор счетчика.
     */
    private Long id;

    /**
     * Уникальный идентификатор пользователя, которому принадлежит счетчик.
     */
    private Long userId;

    /**
     * Текущие показания счётчика.
     */
    private Double counter;

    /**
     * Тип счётчика.
     */
    private String type;

    /**
     * Дата считывания показаний счетчика.
     */
    private LocalDate readingsDate;
}
