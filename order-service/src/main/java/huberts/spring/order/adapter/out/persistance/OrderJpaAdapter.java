package huberts.spring.order.adapter.out.persistance;

import huberts.spring.order.adapter.out.persistance.entity.OrderEntity;
import huberts.spring.order.adapter.out.persistance.repository.OrderRepository;
import huberts.spring.order.domain.model.OrderDomainModel;
import huberts.spring.order.domain.port.out.OrderJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static huberts.spring.order.adapter.out.persistance.mapper.OrderJpaMapper.ORDER_JPA_MAPPER;

@Component
@RequiredArgsConstructor
public class OrderJpaAdapter implements OrderJpaPort {

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
}