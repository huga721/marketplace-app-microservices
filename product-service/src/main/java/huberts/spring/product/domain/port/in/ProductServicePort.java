package huberts.spring.product.domain.port.in;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.domain.model.ProductDomainModel;

import java.util.List;

public interface ProductServicePort {

    ProductDomainModel createProduct(ProductRequest itemRequest, String keycloakId);

    List<ProductDomainModel> getActiveProducts();
    List<ProductDomainModel> getAllProducts();
    ProductDomainModel getProductById(Long productId);

    List<ProductDomainModel> setProductsAsInactive(List<Long> productId);
    ProductDomainModel editProductByIdAndKeycloakId(Long productId, String keycloakId, ProductRequest productRequest);
    ProductDomainModel editProductById(Long productId, ProductRequest productRequest);

    void deleteProductByIdAndKeycloakId(Long productId, String keycloakId);
    void deleteProductById(Long productId);

    List<ProductDomainModel> getProductsByKeycloakId(String keycloakId);
}
