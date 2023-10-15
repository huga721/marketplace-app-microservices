package huberts.spring.basket.application.exception;

public class UnauthorizedProductAdditionException extends RuntimeException{
    public UnauthorizedProductAdditionException(String message) {
        super(message);
    }
}