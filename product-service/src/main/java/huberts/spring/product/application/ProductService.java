package huberts.spring.product.application;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.domain.model.ProductDomainModel;
import huberts.spring.product.domain.model.Status;
import huberts.spring.product.domain.port.in.ProductServicePort;
import huberts.spring.product.domain.port.out.ProductJpaPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductService implements ProductServicePort {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductJpaPort productJpaPort;

    @Override
    public ProductDomainModel createProduct(ProductRequest productRequest, String keycloakId) {
        LOGGER.info(">> ProductService: creating a new product: {}", productRequest.name());

        ProductDomainModel product = new ProductDomainModel();
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setQuality(productRequest.quality().name());
        product.setStatus(Status.ACTIVE);
        product.setCreatedTime(LocalDateTime.now());
        product.setKeycloakId(keycloakId);

        ProductDomainModel productSaved = productJpaPort.saveProduct(product);

        LOGGER.info(">> ProductService: new product saved: {}", productSaved);
        return productSaved;
    }

    @Override
    public List<ProductDomainModel> getActiveProducts() {
        LOGGER.info(">> ProductService: getting products with status active");
        return productJpaPort.getActiveProducts();
    }

    @Override
    public List<ProductDomainModel> getAllProducts() {
        LOGGER.info(">> ProductService: getting all products");
        return productJpaPort.getAllProducts();
    }

    @Override
    public ProductDomainModel getProductById(Long productId) {
        LOGGER.info(">> ProductService: getting product with id: {}", productId);
        ProductDomainModel productDomainModel = productJpaPort.getProductById(productId);
        LOGGER.info(">> ProductService: getting product: {}", productDomainModel);
        return productDomainModel;
    }

    @Override
    public List<ProductDomainModel> setProductsAsInactive(List<Long> productsId) {
        LOGGER.info(">> ProductService: setting products with id as inactive: {}", productsId);

        List<ProductDomainModel> products = productJpaPort.findProductsById(productsId).stream()
                .map(ProductDomainModel::markInactive)
                .toList();
        List<ProductDomainModel> productsChanged = productJpaPort.saveProducts(products);
        LOGGER.info(">> ProductService: products saved: {}", productsChanged);
        return productsChanged;
    }

    @Override
    public ProductDomainModel editProductById(Long productId, ProductRequest productRequest) {
        LOGGER.info(">> ProductService: editing: {} product with id: {}", productRequest, productId);
        ProductDomainModel product = productJpaPort.getProductById(productId);
        product.update(productRequest);

        return productJpaPort.saveProduct(product);
    }

    @Override
    public void deleteProductById(Long productId) {
        LOGGER.info(">> ProductService: deleting product with id: {}", productId);
        ProductDomainModel productDomain = productJpaPort.getProductById(productId);
        productJpaPort.deleteProduct(productDomain);
    }

    @Override
    public ProductDomainModel editProductByIdAndKeycloakId(Long productId, String keycloakId, ProductRequest productRequest) {
        LOGGER.info(">> ProductService: editing: {} product with id: {} keycloak id: {}", productRequest, productId, keycloakId);
        ProductDomainModel product = productJpaPort.getProductByIdAndKeycloakId(productId, keycloakId);
        product.update(productRequest);
        return product;
    }

    @Override
    public void deleteProductByIdAndKeycloakId(Long productId, String keycloakId) {
        LOGGER.info(">> ProductService: deleting product with id: {} keycloak id: {}", productId, keycloakId);
        ProductDomainModel product = productJpaPort.getProductByIdAndKeycloakId(productId, keycloakId);
        productJpaPort.deleteProduct(product);
    }

    @Override
    public List<ProductDomainModel> getProductsByKeycloakId(String keycloakId) {
        LOGGER.info(">> ProductService: getting active products with keycloak id: {}", keycloakId);
        return productJpaPort.getActiveProductsByKeycloakId(keycloakId);
    }
}
