package huberts.spring.order.application;

import huberts.spring.order.adapter.in.web.resource.OrderRequest;
import huberts.spring.order.adapter.out.feign.basket.BasketFeignClient;
import huberts.spring.order.adapter.out.feign.basket.model.BasketDomainModel;
import huberts.spring.order.domain.model.OrderDomainModel;
import huberts.spring.order.domain.port.in.KafkaServicePort;
import huberts.spring.order.domain.port.in.OrderServicePort;
import huberts.spring.order.domain.port.out.OrderJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServicePort {

    private final OrderJpaPort orderJpaPort;
    private final KafkaServicePort kafkaServicePort;
    private final BasketFeignClient basketFeignClient;

    @Override
    public OrderDomainModel createOrder(OrderRequest orderRequest, String keycloakId) {
        BasketDomainModel basket = basketFeignClient.getBasketDetails();

        OrderDomainModel order = new OrderDomainModel();
        order.setKeycloakId(keycloakId);
        order.setBasketId(basket.getId());
        order.setPaymentType(orderRequest.paymentType());
        order.setAddress(orderRequest.deliveryAddress().address());
        order.setPostalCode(orderRequest.deliveryAddress().postalCode());

        OrderDomainModel orderSaved = orderJpaPort.saveOrder(order);
        kafkaServicePort.sendBasketInactiveEvent(basket);
        return orderSaved;
    }

    @Override
    public List<OrderDomainModel> getOrders() {
        return orderJpaPort.getAllOrders();
    }
}