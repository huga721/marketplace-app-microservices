package huberts.spring.product.common.exception;

public class ProductStatusException extends RuntimeException {
    public ProductStatusException(String message) {
        super(message);
    }
}