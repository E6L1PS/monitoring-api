package ru.ylab.application.out;

import ru.ylab.adapters.out.persistence.entity.AuditEntity;

import java.util.List;

/**
 * интерфейс AuditRepository предоставляет методы для работы с аудитом.
 *
 * @author Pesternikov Danil
 */
public interface AuditRepository {

    /**
     * Возвращает список всех аудит-записей.
     *
     * @return Список всех аудит-записей.
     */
    List<AuditEntity> findAll();

    /**
     * Сохраняет новую аудит-запись.
     *
     * @param auditEntity Аудит-запись для сохранения.
     */
    void save(AuditEntity auditEntity);
}
