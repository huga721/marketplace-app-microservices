package huberts.spring.basket.application.exception;

public class FeignAuthorizationException extends RuntimeException{
    public FeignAuthorizationException(String message) {
        super(message);
    }
}