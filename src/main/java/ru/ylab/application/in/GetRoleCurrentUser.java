package ru.ylab.application.in;

import ru.ylab.domain.model.Role;

/**
 * Интерфейс GetRoleCurrentUser предоставляет метод для получения роли текущего пользователя.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который выполняет операцию
 * для получения роли текущего пользователя.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику получения
 * роли текущего пользователя.</p>
 *
 * @author Pesternikov Danil
 */
public interface GetRoleCurrentUser {

    /**
     * Выполняет операцию для получения роли текущего пользователя.
     *
     * @return Объект типа Role, представляющий роль текущего пользователя.
     */
    Role execute();
}
