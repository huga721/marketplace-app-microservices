package huberts.spring.product.common.exception;

public class ProductDoesntExistException extends RuntimeException {
    public ProductDoesntExistException(String message) {
        super(message);
    }
}