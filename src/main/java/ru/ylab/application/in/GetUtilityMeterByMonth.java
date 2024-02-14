package ru.ylab.application.in;

import ru.ylab.domain.model.UtilityMeter;

import java.util.List;

/**
 * Интерфейс GetUtilityMeterByMonth предоставляет метод для получения списка моделей счетчиков коммунальных услуг за указанный месяц.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который выполняет операцию
 * для получения списка моделей счетчиков коммунальных услуг за указанный месяц.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику получения
 * счетчиков коммунальных услуг за указанный месяц.</p>
 *
 * @author Pesternikov Danil
 */
public interface GetUtilityMeterByMonth {

    /**
     * Выполняет операцию для получения списка моделей счетчиков коммунальных услуг за указанный месяц.
     *
     * @param month  Месяц, за который необходимо получить показания.
     * @param userId id пользователя.
     * @return Список объектов типа UtilityMeterModel, представляющих счетчикики коммунальных услуг за указанный месяц.
     */
    List<UtilityMeter> execute(Integer month, Long userId);
}
