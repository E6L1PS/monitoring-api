package ru.ylab.application.in;

import ru.ylab.adapters.in.web.dto.UtilityMeterModel;

import java.util.List;

/**
 * Интерфейс GetUtilityMeter предоставляет метод для получения списка моделей счетчиков коммунальных услуг.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который выполняет операцию
 * для получения списка моделей счетчиков коммунальных услуг.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику получения
 * счетчиков коммунальных услуг.</p>
 *
 * @author Pesternikov Danil
 */
public interface GetLastUtilityMeter {

    /**
     * Выполняет операцию для получения списка моделей счетчиков коммунальных услуг.
     *
     * @return Список объектов типа UtilityMeterModel, представляющих счетчикики коммунальных услуг.
     */
    List<UtilityMeterModel> execute(Long userId);
}
