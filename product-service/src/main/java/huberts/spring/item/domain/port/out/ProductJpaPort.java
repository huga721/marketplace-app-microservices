package huberts.spring.item.domain.port.out;

import huberts.spring.item.adapter.in.web.resource.ProductRequest;
import huberts.spring.item.domain.model.ProductDomainModel;

import java.util.List;

public interface ProductJpaPort {

    ProductDomainModel createProduct(ProductRequest productRequest, String keycloakId);

    List<ProductDomainModel> getAllProducts();
    ProductDomainModel getProductById(Long productId);

    void deleteProductById(Long productId);
}