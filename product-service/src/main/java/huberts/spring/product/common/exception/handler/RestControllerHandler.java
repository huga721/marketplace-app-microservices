package huberts.spring.product.common.exception.handler;

import huberts.spring.product.common.exception.ProductDoesntExistException;
import huberts.spring.product.common.exception.ProductStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(value = ProductDoesntExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productDoesntExistException(ProductDoesntExistException e) {
        return e.getMessage();
    }

    @ExceptionHandler(value = ProductStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productStatusException(ProductStatusException e) {
        return e.getMessage();
    }
}