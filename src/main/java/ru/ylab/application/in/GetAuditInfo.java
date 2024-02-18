package ru.ylab.application.in;

import ru.ylab.domain.model.Audit;

import java.util.List;

/**
 * Интерфейс GetAuditInfo предоставляет метод для получения информации об аудите.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который выполняет операцию
 * получения списка сущностей аудита.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику получения
 * информации об аудите.</p>
 *
 * @author Pesternikov Danil
 */
public interface GetAuditInfo {

    /**
     * Выполняет операцию получения информации об аудите.
     *
     * @return Список объектов типа AuditEntity, представляющих результаты операции аудита.
     */
    List<Audit> execute();
}
