package huberts.spring.order.adapter.out.feign.basket.model.exception;

public class BasketNotFoundException extends RuntimeException {
    public BasketNotFoundException(String message) {
        super(message);
    }
}