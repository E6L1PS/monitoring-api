package ru.ylab.application.out;

import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.domain.model.Role;

/**
 * интерфейс UserRepository предоставляет методы для работы с пользователями.
 *
 * @author Pesternikov Danil
 */
public interface UserRepository {

    /**
     * Сохраняет информацию о пользователе.
     *
     * @param userEntity Объект UserEntity, представляющий пользователя для сохранения.
     */
    void save(UserEntity userEntity);

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
     * Получает текущее имя пользователя.
     *
     * @return Текущее имя пользователя.
     */
    String getCurrentUsername();

    /**
     * Получает роль текущего пользователя.
     *
     * @return Роль текущего пользователя.
     */
    Role getCurrentRoleUser();

    /**
     * Настраивает текущего пользователя.
     *
     * @param userEntity Объект UserEntity, представляющий пользователя для настройки.
     * @return Объект UserEntity, представляющий настроенного пользователя.
     */
    UserEntity setupCurrentUser(UserEntity userEntity);

    /**
     * Выполняет выход текущего пользователя.
     */
    void logout();
}
