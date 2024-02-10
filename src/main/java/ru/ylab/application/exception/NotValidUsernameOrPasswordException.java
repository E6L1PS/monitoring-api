package ru.ylab.application.exception;

/**
 * Исключение, выбрасываемое при неверном имени пользователя или пароле.
 *
 * @author Pesternikov Danil
 */
public class NotValidUsernameOrPasswordException extends RuntimeException {

    public NotValidUsernameOrPasswordException(String message) {
        super(message);
    }
}
