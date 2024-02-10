package ru.ylab.application.exception;

/**
 * Исключение, выбрасываемое при неверном типе счетчика.
 *
 * @author Pesternikov Danil
 */
public class NotValidMeterTypeException extends RuntimeException {

    public NotValidMeterTypeException(String message) {
        super(message);
    }
}
