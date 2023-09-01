package huberts.spring.item.common.exception;

public class ProductDoesntExistException extends RuntimeException {
    public ProductDoesntExistException(String message) {
        super(message);
    }
}
