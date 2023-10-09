package huberts.spring.authentication.application.exception.handler;

import huberts.spring.authentication.application.exception.UserDoesntExistException;
import huberts.spring.authentication.application.exception.model.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(value = UserDoesntExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage userExistException(UserDoesntExistException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(e.getMessage());
        exceptionMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        return exceptionMessage;
    }
}
