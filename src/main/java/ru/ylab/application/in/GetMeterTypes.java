package ru.ylab.application.in;

import ru.ylab.adapters.in.web.dto.MeterTypeDto;

import java.util.List;

/**
 * Интерфейс GetUtilityMeterTypes предоставляет метод для получения списка типов счетчика.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который выполняет операцию
 * для получения списка типов счетчика.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику получения
 * доступных типов счетчика.</p>
 *
 * @author Pesternikov Danil
 */
public interface GetMeterTypes {

    /**
     * Выполняет операцию для получения списка типов счетчика.
     *
     * @return Список объектов типа MeterType, представляющих доступные типы счетчиков.
     */
    List<MeterTypeDto> execute();
}
