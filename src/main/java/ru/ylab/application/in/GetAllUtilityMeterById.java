package ru.ylab.application.in;

import ru.ylab.domain.model.UtilityMeter;

import java.util.List;

/**
 * интерфейс для получения списка счетчиков коммунальных услуг по id пользователя.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который возвращает
 * список моделей счетчика коммунальных услуг по id пользователя.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику для
 * получения списка счетчиков коммунальных услуг по id пользователя.</p>
 *
 * @author Pesternikov Danil
 */
public interface GetAllUtilityMeterById {

    /**
     * Возвращает список моделей счетчика коммунальных услуг по id пользователя.
     *
     * @return Список моделей счетчика коммунальных услуг.
     */
    List<UtilityMeter> execute(Long userId);
}
