package huberts.spring.order.application.exception;

public class FeignAuthorizationException extends RuntimeException{
    public FeignAuthorizationException(String message) {
        super(message);
    }
}