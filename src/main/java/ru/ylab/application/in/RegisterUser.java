package ru.ylab.application.in;

import ru.ylab.adapters.in.web.dto.RegisterDto;

/**
 * Интерфейс RegisterUser предоставляет метод для регистрации нового пользователя.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который выполняет операцию
 * регистрации нового пользователя на основе предоставленной модели регистрации.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику регистрации
 * нового пользователя.</p>
 *
 * @author Pesternikov Danil
 */
public interface RegisterUser {

    /**
     * Выполняет операцию регистрации нового пользователя.
     *
     * @param registerDto Модель регистрации, содержащая информацию о новом пользователе.
     * @return id созданного пользователя.
     */
    Long execute(RegisterDto registerDto);
}
