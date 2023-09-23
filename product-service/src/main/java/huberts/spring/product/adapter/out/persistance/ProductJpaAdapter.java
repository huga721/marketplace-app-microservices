package huberts.spring.product.adapter.out.persistance;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.adapter.out.persistance.entity.ProductEntity;
import huberts.spring.product.adapter.out.persistance.repository.ProductRepository;
import huberts.spring.product.domain.model.ProductDomainModel;
import huberts.spring.product.domain.port.out.ProductJpaPort;
import huberts.spring.product.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static huberts.spring.product.adapter.out.persistance.mapper.ProductJpaMapper.PRODUCT_JPA_MAPPER;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductJpaAdapter implements ProductJpaPort {

    private final ProductRepository productRepository;

    @Override
    public ProductDomainModel createProduct(ProductRequest productRequest, String keycloakId) {
        ProductEntity productEntity = PRODUCT_JPA_MAPPER.toJpaEntity(productRequest, keycloakId);
        productRepository.save(productEntity);
        log.info("Product with id = {} was created successfully", productEntity.getId());
        return PRODUCT_JPA_MAPPER.toDomainModel(productEntity);
    }

    @Override
    public List<ProductDomainModel> getAllProducts() {
        log.info("Getting all products");
        return productRepository.findAll()
                .stream()
                .map(PRODUCT_JPA_MAPPER::toDomainModel)
                .toList();
    }

    @Override
    public ProductDomainModel getProductById(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Product with id = %s doesn't exist.", productId);
                    log.warn("An exception occurred!", new ProductNotFoundException(errorMessage));
                    return new ProductNotFoundException(errorMessage);
                });
        return PRODUCT_JPA_MAPPER.toDomainModel(productEntity);
    }

    @Override
    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }
}
