package huberts.spring.order.domain.port.in;

import huberts.spring.order.adapter.in.web.resource.OrderRequest;
import huberts.spring.order.domain.model.OrderDomainModel;

import java.util.List;

public interface OrderServicePort {

    OrderDomainModel createOrder(OrderRequest orderRequest, String keycloakId);

    List<OrderDomainModel> getOrders();
}
