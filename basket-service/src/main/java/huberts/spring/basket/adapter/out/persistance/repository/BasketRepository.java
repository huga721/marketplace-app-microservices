package huberts.spring.basket.adapter.out.persistance.repository;

import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
}
