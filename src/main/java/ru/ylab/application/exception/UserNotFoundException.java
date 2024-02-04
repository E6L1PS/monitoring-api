package ru.ylab.application.exception;

/**
 * Исключение, выбрасываемое при отсутствии пользователя.
 *
 * @author Pesternikov Danil
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
