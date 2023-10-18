package huberts.spring.order.adapter.out.persistance.repository;

import huberts.spring.order.adapter.out.persistance.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}