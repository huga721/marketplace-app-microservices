package huberts.spring.basket.application.exception.handler;

import feign.FeignException;
import huberts.spring.basket.application.exception.BasketNotFoundException;
import huberts.spring.basket.application.exception.FeignAuthorizationException;
import huberts.spring.basket.application.exception.ProductInBasketException;
import huberts.spring.basket.application.exception.UnauthorizedProductAdditionException;
import huberts.spring.basket.application.exception.model.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(value = BasketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage basketNotFoundException(BasketNotFoundException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(e.getMessage());
        exceptionMessage.setStatus(HttpStatus.NOT_FOUND.value());
        return exceptionMessage;
    }

    @ExceptionHandler(value = FeignAuthorizationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage feignAuthorizationException(FeignAuthorizationException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(e.getMessage());
        exceptionMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        return exceptionMessage;
    }

    @ExceptionHandler(value = ProductInBasketException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage productInBasketException(ProductInBasketException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(e.getMessage());
        exceptionMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        return exceptionMessage;
    }

    @ExceptionHandler(value = UnauthorizedProductAdditionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage unauthorizedProductAdditionException(UnauthorizedProductAdditionException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(e.getMessage());
        exceptionMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        return exceptionMessage;
    }
}