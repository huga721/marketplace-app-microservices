package huberts.spring.product.application;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.domain.model.ProductDomainModel;
import huberts.spring.product.domain.port.in.ProductServicePort;
import huberts.spring.product.domain.port.out.ProductJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductService implements ProductServicePort {

    private final ProductJpaPort productJpaPort;

    @Override
    public ProductDomainModel createProduct(ProductRequest productRequest, String keycloakId) {
        return productJpaPort.createProduct(productRequest, keycloakId);
    }

    @Override
    public List<ProductDomainModel> getAvailableProducts() {
        return productJpaPort.getAllProducts()
                .stream()
                .map(ProductDomainModel::returnActiveProduct)
                .toList();
    }

    @Override
    public ProductDomainModel getProductById(Long productId) {
        return productJpaPort.getProductById(productId);
    }

    @Override
    public ProductDomainModel editProductById(Long productId, ProductRequest productRequest) {
        ProductDomainModel productDomain = productJpaPort.getProductById(productId);
        productDomain.update(productRequest);
        return productDomain;
    }

    @Override
    public void deleteProductById(Long productId) {
        ProductDomainModel productDomain = productJpaPort.getProductById(productId);
        if (productDomain != null) {
            productJpaPort.deleteProductById(productId);
        }
    }
}
