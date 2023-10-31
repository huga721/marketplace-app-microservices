package huberts.spring.user.application.exception.handler;

import huberts.spring.user.application.exception.UserNotFoundException;
import huberts.spring.user.application.exception.model.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage userNotFound(UserNotFoundException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(e.getMessage());
        exceptionMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        return exceptionMessage;
    }
}
