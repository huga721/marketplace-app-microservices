package huberts.spring.product.domain.port.in;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.domain.model.ProductDomainModel;

import java.util.List;

public interface ProductServicePort {

    ProductDomainModel createProduct(ProductRequest itemRequest, String keycloakId);

    List<ProductDomainModel> getAvailableProducts();
    ProductDomainModel getProductById(Long productId);

    ProductDomainModel editProductById(Long productId, ProductRequest productRequest);

    void deleteProductById(Long productId);
}
