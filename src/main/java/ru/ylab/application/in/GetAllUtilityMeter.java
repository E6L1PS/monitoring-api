package ru.ylab.application.in;

import ru.ylab.application.model.UtilityMeterModel;

import java.util.List;


/**
 * интерфейс для получения списка счетчиков коммунальных услуг.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который возвращает
 * список моделей счетчика коммунальных услуг.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику для
 * получения списка счетчиков коммунальных услугв.</p>
 *
 * @author Pesternikov Danil
 */
public interface GetAllUtilityMeter {

    /**
     * Возвращает список моделей счетчика коммунальных услуг.
     *
     * @return Список моделей счетчика коммунальных услуг.
     */
    List<UtilityMeterModel> execute();
}
