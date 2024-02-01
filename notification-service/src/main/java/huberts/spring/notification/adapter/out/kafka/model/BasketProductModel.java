package huberts.spring.notification.adapter.out.kafka.model;

public record BasketProductModel(
        Long id,
        Long productId,
        Long productValue,
        Long basketId) {
}
