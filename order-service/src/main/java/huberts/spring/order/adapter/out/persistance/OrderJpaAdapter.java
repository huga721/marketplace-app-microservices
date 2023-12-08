package huberts.spring.order.adapter.out.persistance;

import huberts.spring.order.adapter.out.feign.basket.model.exception.BasketNotFoundException;
import huberts.spring.order.adapter.out.persistance.entity.OrderEntity;
import huberts.spring.order.adapter.out.persistance.repository.OrderRepository;
import huberts.spring.order.domain.model.OrderDomainModel;
import huberts.spring.order.domain.port.out.OrderJpaPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static huberts.spring.order.adapter.out.persistance.mapper.OrderJpaMapper.ORDER_JPA_MAPPER;

@Component
@RequiredArgsConstructor
public class OrderJpaAdapter implements OrderJpaPort {

    private final Logger  LOGGER = LoggerFactory.getLogger(OrderJpaAdapter.class);

    private final OrderRepository orderRepository;

    @Override
    public OrderDomainModel saveOrder(OrderDomainModel orderDomainModel) {
        OrderEntity orderEntity = ORDER_JPA_MAPPER.toJpaEntity(orderDomainModel);
        OrderEntity orderSaved = orderRepository.save(orderEntity);
        return ORDER_JPA_MAPPER.toDomainModel(orderSaved);
    }

    @Override
    public List<OrderDomainModel> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(ORDER_JPA_MAPPER::toDomainModel)
                .toList();
    }

    @Override
    public OrderDomainModel getOrderById(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Basket with id = %s doesn't exist.", orderId);
                    LOGGER.warn("An exception occurred!", new BasketNotFoundException(errorMessage));
                    return new BasketNotFoundException(errorMessage);
                });
        return ORDER_JPA_MAPPER.toDomainModel(order);
    }

    @Override
    public List<OrderDomainModel> getOrdersByKeycloakId(String keycloakId) {
        List<OrderEntity> order = orderRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> {
                    String errorMessage = "Authenticated user doesn't have any orders.";
                    LOGGER.warn("An exception occurred!", new BasketNotFoundException(errorMessage));
                    return new BasketNotFoundException(errorMessage);
                });
        return order.stream()
                .map(ORDER_JPA_MAPPER::toDomainModel)
                .collect(Collectors.toList());
    }
}