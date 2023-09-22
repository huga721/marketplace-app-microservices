package huberts.spring.product.domain.port.out;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.domain.model.ProductDomainModel;

import java.util.List;

public interface ProductJpaPort {

    ProductDomainModel createProduct(ProductRequest productRequest, String keycloakId);

    List<ProductDomainModel> getAllProducts();
    ProductDomainModel getProductById(Long productId);

    void deleteProductById(Long productId);
}