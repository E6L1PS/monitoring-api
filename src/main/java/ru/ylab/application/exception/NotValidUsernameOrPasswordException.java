package ru.ylab.application.exception;

public class NotValidUsernameOrPasswordException extends RuntimeException {

    public NotValidUsernameOrPasswordException(String message) {
        super(message);
    }
}
