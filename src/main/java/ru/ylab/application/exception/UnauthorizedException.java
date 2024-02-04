package ru.ylab.application.exception;

/**
 * Исключение, выбрасываемое при отсутствии авторизации.
 *
 * @author Pesternikov Danil
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
