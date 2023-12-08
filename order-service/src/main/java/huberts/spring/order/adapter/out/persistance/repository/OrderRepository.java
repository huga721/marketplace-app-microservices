package huberts.spring.order.adapter.out.persistance.repository;

import huberts.spring.order.adapter.out.persistance.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<List<OrderEntity>> findByKeycloakId(String keycloakId);
}