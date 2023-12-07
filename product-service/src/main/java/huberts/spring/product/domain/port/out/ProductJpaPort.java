package huberts.spring.product.domain.port.out;

import huberts.spring.product.domain.model.ProductDomainModel;

import java.util.List;

public interface ProductJpaPort {

    ProductDomainModel saveProduct(ProductDomainModel productDomain);
    List<ProductDomainModel> saveProducts(List<ProductDomainModel> productsDomain);

    List<ProductDomainModel> getActiveProducts();
    List<ProductDomainModel> getAllProducts();
    ProductDomainModel getProductById(Long productId);
    ProductDomainModel getProductByIdAndKeycloakId(Long productId, String keycloakId);

    void deleteProduct(ProductDomainModel productDomainModel);

    List<ProductDomainModel> findProductsById(List<Long> productId);

    List<ProductDomainModel> getActiveProductsByKeycloakId(String keycloakId);
}