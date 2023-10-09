package huberts.spring.basket.adapter.out.feign.product.model.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}