package huberts.spring.basket.adapter.out.persistance.repository;

import huberts.spring.basket.adapter.out.persistance.entity.BasketProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketProductRepository extends JpaRepository<BasketProductEntity, Long> {
}
