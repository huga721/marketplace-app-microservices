package huberts.spring.order.adapter.in.web.resource;

public record OrderRequest(DeliveryAddress deliveryAddress, PaymentType paymentType) {
}