package huberts.spring.product.adapter.out.persistance.repository;

import huberts.spring.product.adapter.out.persistance.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
//    void deleteById(Long productId)
}