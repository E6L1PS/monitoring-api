package ru.ylab.adapters.in.web.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ylab.application.exception.*;

/**
 * Создан: 21.02.2024.
 *
 * @author Pesternikov Danil
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleUserNotFoundException(UserNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IncorrectPasswordException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleIncorrectPasswordException(IncorrectPasswordException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MonthlySubmitLimitException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleMonthlySubmitLimitException(MonthlySubmitLimitException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotValidMeterTypeException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleNotValidMeterTypeException(NotValidMeterTypeException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotValidUsernameOrPasswordException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleNotValidUsernameOrPasswordException(NotValidUsernameOrPasswordException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UsernameAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleNotValidUsernameOrPasswordException(UsernameAlreadyExistsException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseError handleBadCredentialsException(BadCredentialsException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
//TODO delete:
    @ExceptionHandler({ExpiredJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseError handleExpiredJwtException(ExpiredJwtException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({SignatureException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseError handleSignatureException(SignatureException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({JwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseError handleJwtException(JwtException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
