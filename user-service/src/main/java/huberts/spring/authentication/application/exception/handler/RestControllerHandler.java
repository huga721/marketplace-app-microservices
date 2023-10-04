package huberts.spring.authentication.application.exception.handler;

import huberts.spring.authentication.application.exception.UserDoesntExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(value = UserDoesntExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userExistException(UserDoesntExistException e) {
        return e.getMessage();
    }
}
