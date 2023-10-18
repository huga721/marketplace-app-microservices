package huberts.spring.order.application.exception.handler;

import huberts.spring.order.application.exception.FeignAuthorizationException;
import huberts.spring.order.application.exception.OrderCompleteException;
import huberts.spring.order.application.exception.model.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(value = FeignAuthorizationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage productNotFoundException(FeignAuthorizationException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(e.getMessage());
        exceptionMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        return exceptionMessage;
    }

    @ExceptionHandler(value = OrderCompleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage productStatusException(OrderCompleteException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(e.getMessage());
        exceptionMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        return exceptionMessage;
    }
}