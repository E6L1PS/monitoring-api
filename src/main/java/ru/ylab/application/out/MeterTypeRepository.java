package ru.ylab.application.out;

import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;

import java.util.List;

/**
 * интерфейс MeterTypeRepository предоставляет методы для работы с типами счетчиков.
 *
 * @author Pesternikov Danil
 */
public interface MeterTypeRepository {

    /**
     * Получает все типы счетчиков.
     *
     * @return Список объектов MeterTypeEntity, представляющих все типы счетчиков.
     */
    List<MeterTypeEntity> findAll();

    /**
     * Создает новый тип счетчика с указанным именем.
     *
     * @param meterTypeEntity имя нового типа счетчика.
     * @return Объект MeterTypeEntity, представляющий созданный тип счетчика.
     */
    MeterTypeEntity save(MeterTypeEntity meterTypeEntity);

    /**
     * Проверяет, является ли указанный тип счетчика допустимым.
     *
     * @return true, если тип счетчика допустим, в противном случае - false.
     */
    Boolean isMeterTypeExists(String typeName);
}
