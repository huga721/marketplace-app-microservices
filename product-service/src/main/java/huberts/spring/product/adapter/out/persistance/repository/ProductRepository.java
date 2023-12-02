package huberts.spring.product.adapter.out.persistance.repository;

import huberts.spring.product.adapter.out.persistance.entity.ProductEntity;
import huberts.spring.product.adapter.out.persistance.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByIdAndKeycloakId(Long id, String keycloakId);
    List<ProductEntity> findAllByKeycloakIdAndStatus(String keycloakId, Status status);
    List<ProductEntity> findAllByStatus(Status status);
}