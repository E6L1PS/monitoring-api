package ru.ylab.application.in;

import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.domain.model.User;

/**
 * Интерфейс LoginUser предоставляет метод для аутентификации пользователя.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который выполняет операцию
 * аутентификации пользователя на основе предоставленной модели входа.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику аутентификации
 * пользователя.</p>
 *
 * @author Pesternikov Danil
 */
public interface LoginUser {

    /**
     * Выполняет операцию аутентификации пользователя.
     *
     * @param user Модель входа, содержащая информацию о пользовательском вводе.
     * @return
     */
    UserEntity execute(User user);
}
