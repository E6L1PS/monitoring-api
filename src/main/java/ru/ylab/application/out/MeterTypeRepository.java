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
     * Проверяет, является ли указанный тип счетчика допустимым.
     *
     * @param meterTypeEntity Объект MeterTypeEntity, представляющий тип счетчика для проверки.
     * @return true, если тип счетчика допустим, в противном случае - false.
     */
    Boolean isValid(MeterTypeEntity meterTypeEntity);

    /**
     * Создает новый тип счетчика с указанным именем.
     *
     * @param typeName имя нового типа счетчика.
     * @return Объект MeterTypeEntity, представляющий созданный тип счетчика.
     */
    MeterTypeEntity createType(String typeName);
}
