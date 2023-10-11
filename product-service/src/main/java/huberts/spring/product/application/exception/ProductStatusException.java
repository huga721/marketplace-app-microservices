package huberts.spring.product.application.exception;

public class ProductStatusException extends RuntimeException {
    public ProductStatusException(String message) {
        super(message);
    }
}