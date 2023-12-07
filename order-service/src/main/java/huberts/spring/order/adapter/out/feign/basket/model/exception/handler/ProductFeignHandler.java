package huberts.spring.order.adapter.out.feign.basket.model.exception.handler;

import huberts.spring.order.adapter.out.feign.basket.model.exception.BasketNotFoundException;
import huberts.spring.order.adapter.out.feign.basket.model.exception.model.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ProductFeignHandler {

    @ExceptionHandler(value = BasketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage productNotFoundException(BasketNotFoundException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(e.getMessage());
        exceptionMessage.setStatus(HttpStatus.NOT_FOUND.value());
        return exceptionMessage;
    }
}