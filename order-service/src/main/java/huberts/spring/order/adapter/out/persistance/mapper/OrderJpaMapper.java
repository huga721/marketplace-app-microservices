package huberts.spring.order.adapter.out.persistance.mapper;

import huberts.spring.order.adapter.out.persistance.entity.OrderEntity;
import huberts.spring.order.domain.model.OrderDomainModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderJpaMapper {

    OrderJpaMapper ORDER_JPA_MAPPER = Mappers.getMapper(OrderJpaMapper.class);

    OrderEntity toJpaEntity(OrderDomainModel orderDomainModel);

    OrderDomainModel toDomainModel(OrderEntity orderEntity);
}