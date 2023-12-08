package huberts.spring.order.domain.port.out;

import huberts.spring.order.domain.model.OrderDomainModel;

import java.util.List;

public interface OrderJpaPort {

    OrderDomainModel saveOrder(OrderDomainModel orderDomainModel);

    List<OrderDomainModel> getAllOrders();

    OrderDomainModel getOrderById(Long orderId);

    List<OrderDomainModel> getOrdersByKeycloakId(String keycloakId);
}
