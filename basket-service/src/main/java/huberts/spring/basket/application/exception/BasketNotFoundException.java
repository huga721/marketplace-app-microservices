package huberts.spring.basket.application.exception;

public class BasketNotFoundException extends RuntimeException{
    public BasketNotFoundException(String message) {
        super(message);
    }
}