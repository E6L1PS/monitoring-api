package ru.ylab.application.in;

import java.util.Map;

/**
 * Интерфейс SubmitUtilityMeter предоставляет метод для подачи показаний счетчиков коммунальных услуг.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который выполняет операцию
 * отправки данных счетчиков коммунальных услуг.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику обработки
 * и сохранения данных счетчиков коммунальных услуг.</p>
 *
 * @author Pesternikov Danil
 */
public interface SubmitUtilityMeter {

    /**
     * Выполняет операцию отправки данных счетчиков коммунальных услуг.
     *
     * @param utilityMeters Словарь, содержащий данные счетчиков в формате "название счетчика" -> "значение".
     * @param userId id пользователя.
     */
    void execute(Map<String, Double> utilityMeters, Long userId);
}
