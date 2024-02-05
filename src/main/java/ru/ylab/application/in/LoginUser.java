package ru.ylab.application.in;

import ru.ylab.application.model.LoginModel;

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
     * @param loginModel Модель входа, содержащая информацию о пользовательском вводе.
     */
    void execute(LoginModel loginModel);
}
