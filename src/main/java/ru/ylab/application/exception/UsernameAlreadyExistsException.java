package ru.ylab.application.exception;

/**
 * Исключение, выбрасываемое при попытке создать пользователя с уже существующим именем пользователя.
 *
 * @author Pesternikov Danil
 */
public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
