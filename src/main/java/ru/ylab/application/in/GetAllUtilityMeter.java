package ru.ylab.application.in;

import ru.ylab.application.model.UtilityMeterModel;

import java.util.List;


/**
 * интерфейс для получения списка утилитарных счётчиков.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который возвращает
 * список моделей утилитарных счётчиков.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику для
 * получения списка утилитарных счётчиков.</p>
 *
 * @author Pesternikov Danil
 */
public interface GetAllUtilityMeter {

    /**
     * Возвращает список моделей утилитарных счётчиков.
     *
     * @return Список моделей утилитарных счётчиков.
     */
    List<UtilityMeterModel> execute();
}
