package huberts.spring.product.adapter.out.persistance;

import huberts.spring.product.adapter.out.persistance.entity.ProductEntity;
import huberts.spring.product.adapter.out.persistance.entity.Status;
import huberts.spring.product.adapter.out.persistance.repository.ProductRepository;
import huberts.spring.product.application.exception.ProductNotFoundException;
import huberts.spring.product.domain.model.ProductDomainModel;
import huberts.spring.product.domain.port.out.ProductJpaPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static huberts.spring.product.adapter.out.persistance.mapper.ProductJpaMapper.PRODUCT_JPA_MAPPER;

@Component
@RequiredArgsConstructor
public class ProductJpaAdapter implements ProductJpaPort {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductJpaAdapter.class);
    private final ProductRepository productRepository;

    @Override
    public ProductDomainModel saveProduct(ProductDomainModel productDomain) {
        ProductEntity productEntity = PRODUCT_JPA_MAPPER.toJpaEntity(productDomain);
        ProductEntity productSaved = productRepository.save(productEntity);
        return PRODUCT_JPA_MAPPER.toDomainModel(productSaved);
    }

    @Override
    public List<ProductDomainModel> saveProducts(List<ProductDomainModel> productsDomain) {
        List<ProductEntity> productsEntities = productsDomain.stream()
                .map(PRODUCT_JPA_MAPPER::toJpaEntity)
                .toList();
        List<ProductEntity> productsSaved = productRepository.saveAll(productsEntities);
        return productsSaved.stream()
                .map(PRODUCT_JPA_MAPPER::toDomainModel)
                .toList();
    }

    @Override
    public List<ProductDomainModel> getActiveProducts() {
        return productRepository.findAllByStatus(Status.ACTIVE)
                .stream()
                .map(PRODUCT_JPA_MAPPER::toDomainModel)
                .toList();
    }

    @Override
    public List<ProductDomainModel> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(PRODUCT_JPA_MAPPER::toDomainModel)
                .toList();
    }

    @Override
    public ProductDomainModel getProductById(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Product with id = %s doesn't exist.", productId);
                    LOGGER.warn("An exception occurred!", new ProductNotFoundException(errorMessage));
                    return new ProductNotFoundException(errorMessage);
                });
        return PRODUCT_JPA_MAPPER.toDomainModel(product);
    }

    @Override
    public ProductDomainModel getProductByIdAndKeycloakId(Long productId, String keycloakId) {
        ProductEntity product = productRepository.findByIdAndKeycloakId(productId, keycloakId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Product with id = %s and keycloak id = %s doesn't exist.", productId, keycloakId);
                    LOGGER.warn("An exception occurred!", new ProductNotFoundException(errorMessage));
                    return new ProductNotFoundException(errorMessage);
                });
        return PRODUCT_JPA_MAPPER.toDomainModel(product);
    }

    @Override
    public void deleteProduct(ProductDomainModel productDomainModel) {
        ProductEntity productEntity = PRODUCT_JPA_MAPPER.toJpaEntity(productDomainModel);
        productRepository.delete(productEntity);
        LOGGER.info(">> ProductJpaAdapter: deleted product");
    }

    @Override
    public List<ProductDomainModel> findProductsById(List<Long> productId) {
        return productId.stream()
                .map(this::getProductById)
                .toList();
    }

    @Override
    public List<ProductDomainModel> getActiveProductsByKeycloakId(String keycloakId) {
        return productRepository.findAllByKeycloakIdAndStatus(keycloakId, Status.ACTIVE)
                .stream()
                .map(PRODUCT_JPA_MAPPER::toDomainModel)
                .toList();
    }
}