package ru.ylab.application.exception;

/**
 * Исключение, выбрасываемое при достижении ежемесячного предела отправки.
 *
 * @author Pesternikov Danil
 */
public class MonthlySubmitLimitException extends RuntimeException {

    public MonthlySubmitLimitException(String message) {
        super(message);
    }
}