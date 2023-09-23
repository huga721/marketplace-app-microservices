package huberts.spring.basket.common.exception;

public class FeignAuthorizationException extends RuntimeException{
    public FeignAuthorizationException(String message) {
        super(message);
    }
}