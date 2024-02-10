package ru.ylab.application.exception;

/**
 * Исключение, выбрасываемое при некорректном пароле.
 *
 * @author Pesternikov Danil
 */
public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
