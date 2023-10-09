package huberts.spring.basket.application.exception;

public class ProductInBasketException extends RuntimeException{
    public ProductInBasketException(String message) {
        super(message);
    }
}