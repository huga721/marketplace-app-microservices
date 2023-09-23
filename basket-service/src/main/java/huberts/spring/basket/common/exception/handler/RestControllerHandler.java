package huberts.spring.basket.common.exception.handler;

import huberts.spring.basket.common.exception.BasketNotFoundException;
import huberts.spring.basket.common.exception.FeignAuthorizationException;
import huberts.spring.basket.common.exception.ProductInBasketException;
import huberts.spring.basket.common.exception.UnauthorizedProductAdditionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(value = BasketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String basketNotFoundException(BasketNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(value = FeignAuthorizationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String feignAuthorizationException(FeignAuthorizationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(value = ProductInBasketException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String productInBasketException(ProductInBasketException e) {
        return e.getMessage();
    }

    @ExceptionHandler(value = UnauthorizedProductAdditionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String unauthorizedProductAdditionException(UnauthorizedProductAdditionException e) {
        return e.getMessage();
    }
}
