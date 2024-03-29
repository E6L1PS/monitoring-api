package ru.ylab.application.out;

import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;

import java.util.List;

/**
 * интерфейс MeterRepository предоставляет методы для работы с счетчиками коммунальных услуг.
 *
 * @author Pesternikov Danil
 */
public interface MeterRepository {


    /**
     * Получает все счетчики коммунальных услуг.
     *
     * @return Список объектов UtilityMeterEntity, представляющих все счетчики коммунальных услуг.
     */
    List<UtilityMeterEntity> findAll();

    /**
     * Получает все счетчики коммунальных услуг, связанные с определенным именем пользователя.
     *
     * @param userId id пользователя
     * @return Список объектов UtilityMeterEntity, представляющих все счетчики коммунальных услуг для данного пользователя.
     */
    List<UtilityMeterEntity> findAllByUserId(Long userId);

    /**
     * Получает последний счетчик коммунальных услуг для определенного пользователя.
     *
     * @param userId id пользователя
     * @return Список объектов UtilityMeterEntity, представляющих последний счетчик коммунальных услуг для данного пользователя.
     */
    List<UtilityMeterEntity> findLastByUserId(Long userId);

    /**
     * Получает счетчики коммунальных услуг для определенного месяца и пользователя.
     *
     * @param month  Месяц (число от 1 до 12)
     * @param userId id пользователя
     * @return Список объектов UtilityMeterEntity, представляющих счетчики коммунальных услуг для данного пользователя в указанный месяц.
     */
    List<UtilityMeterEntity> findByMonthAndUserId(Integer month, Long userId);

    /**
     * Создает новый счетчик коммунальных услуг.
     *
     * @param utilityMeterEntity Объект UtilityMeterEntity, представляющий новый счетчик коммунальных услуг.
     * @return Созданный счетчик коммунальных услуг.
     */
    UtilityMeterEntity save(UtilityMeterEntity utilityMeterEntity);

    /**
     * Создает счетчики коммунальных услуг.
     *
     * @param utilityMeterEntities Список объектов UtilityMeterEntity, представляющих счетчики коммунальных услуг.
     */
    void saveAll(List<UtilityMeterEntity> utilityMeterEntities);

    /**
     * Проверяет поданы ли показания в текущем месяце.
     *
     * @param userId id пользователя
     * @return True - если уже подано, false - если еще не подано.
     */
    Boolean isSubmitted(Long userId);
}
