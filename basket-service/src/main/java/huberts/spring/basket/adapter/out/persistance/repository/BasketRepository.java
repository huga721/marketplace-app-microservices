package huberts.spring.basket.adapter.out.persistance.repository;

import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import huberts.spring.basket.adapter.out.feign.product.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
    BasketEntity findBasketEntityByKeycloakIdAndStatus(String keycloakId, Status status);
}
