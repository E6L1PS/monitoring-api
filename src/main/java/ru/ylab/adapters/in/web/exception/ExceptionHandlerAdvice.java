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

    @ExceptionHandler({
            UserNotFoundException.class,
            IncorrectPasswordException.class,
            MonthlySubmitLimitException.class,
            NotValidMeterTypeException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleBadRequestException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            NotValidUsernameOrPasswordException.class,
            UsernameAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleConflictException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            BadCredentialsException.class,
            ExpiredJwtException.class,
            SignatureException.class,
            JwtException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseError handleUnauthorizedException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
