package huberts.spring.notification.adapter.out.persistence.exception.handler;

import huberts.spring.notification.adapter.out.persistence.exception.NotificationNotFoundException;
import huberts.spring.notification.adapter.out.persistence.exception.model.ExceptionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestControllerHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(RestControllerHandler.class);

    @ExceptionHandler(value = NotificationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFoundException(NotificationNotFoundException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(e.getMessage());
        exceptionMessage.setStatus(HttpStatus.NOT_FOUND.value());
        return e.getMessage();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage argumentNotValid(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        String errorMessage = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get();

        MethodParameter parameter = e.getParameter();

        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(errorMessage);
        exceptionMessage.setStatus(HttpStatus.BAD_REQUEST.value());

        LOGGER.error("An exception occurred during validation of method arguments!", new MethodArgumentNotValidException(parameter, bindingResult));
        return exceptionMessage;
    }
}