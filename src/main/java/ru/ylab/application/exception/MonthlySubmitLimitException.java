package ru.ylab.application.exception;

public class MonthlySubmitLimitException extends RuntimeException {

    public MonthlySubmitLimitException(String message) {
        super(message);
    }
}