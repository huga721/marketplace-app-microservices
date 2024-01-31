package huberts.spring.order.domain.port.in;

import huberts.spring.order.domain.model.OrderDomainModel;

public interface KafkaNotificationServicePort {
    void sendOrderCreateNotificationEvent(OrderDomainModel orderDomainModel);
}
