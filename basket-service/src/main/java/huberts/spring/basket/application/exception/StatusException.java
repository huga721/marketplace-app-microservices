package huberts.spring.basket.application.exception;

public class StatusException extends RuntimeException {
    public StatusException(String message) {
        super(message);
    }
}