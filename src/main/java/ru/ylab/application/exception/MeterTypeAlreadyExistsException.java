package ru.ylab.application.exception;

/**
 * Создан: 24.02.2024.
 *
 * @author Pesternikov Danil
 */
public class MeterTypeAlreadyExistsException extends RuntimeException {

    public MeterTypeAlreadyExistsException(String message) {
        super(message);
    }
}
