package ru.ylab.application.in;

import ru.ylab.adapters.in.web.dto.UtilityMeterDto;

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
     * @return Список объектов типа UtilityMeterDto, представляющих счетчикики коммунальных услуг за указанный месяц.
     */
    List<UtilityMeterDto> execute(Integer month, Long userId);
}
