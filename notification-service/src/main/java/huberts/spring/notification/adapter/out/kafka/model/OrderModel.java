package huberts.spring.notification.adapter.out.kafka.model;

public record OrderModel(
        Long id,
        String keycloakId,
        Long basketId,
        String paymentType,
        String address,
        String postalCode) {
}
