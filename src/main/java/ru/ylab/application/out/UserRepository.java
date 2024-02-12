package ru.ylab.application.out;

import ru.ylab.adapters.out.persistence.entity.UserEntity;

/**
 * интерфейс UserRepository предоставляет методы для работы с пользователями.
 *
 * @author Pesternikov Danil
 */
public interface UserRepository {

    /**
     * Получает пользователя по его имени пользователя.
     *
     * @param username имя пользователя.
     * @return Объект UserEntity, представляющий пользователя с указанным именем.
     */
    UserEntity getByUsername(String username);

    /**
     * Проверяет, существует ли пользователь с указанным именем.
     *
     * @param username имя пользователя.
     * @return true, если пользователь существует, в противном случае - false.
     */
    Boolean isAlreadyExists(String username);

    /**
     * Сохраняет информацию о пользователе.
     *
     * @param userEntity Объект UserEntity, представляющий пользователя для сохранения.
     */
    Long save(UserEntity userEntity);
}
