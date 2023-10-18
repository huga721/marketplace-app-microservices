package huberts.spring.order.application.exception;

public class OrderCompleteException extends RuntimeException {
    public OrderCompleteException(String message) {
        super(message);
    }
}