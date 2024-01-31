package ru.ylab.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий сущность пользователя.
 *
 * <p>Данный класс использует аннотации Lombok для автоматической генерации
 * кода, также содержит методы для проверки валидности имени пользователя
 * и пароля.</p>
 *
 * @author Pesternikov Danil
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * имя пользователя.
     */
    private String username;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Роль пользователя.
     */
    private Role role;

    /**
     * Проверяет валидность имени пользователя.
     *
     * @return true, если имя пользователя валидно, иначе false.
     */
    public boolean usernameIsValid() {
        return username != null && username.matches("\\w{5,15}");
    }

    /**
     * Проверяет валидность пароля.
     *
     * @return true, если пароль валиден, иначе false.
     */
    public boolean passwordIsValid() {
        return password != null && username.matches("\\w{5,15}");
    }
}