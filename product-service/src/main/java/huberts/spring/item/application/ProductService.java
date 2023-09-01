package huberts.spring.item.application;

import huberts.spring.item.adapter.in.web.resource.ProductRequest;
import huberts.spring.item.domain.model.ProductDomainModel;
import huberts.spring.item.domain.port.in.ProductServicePort;
import huberts.spring.item.domain.port.out.ProductJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductService implements ProductServicePort {

    private final ProductJpaPort productJpaPort;

    @Override
    public ProductDomainModel createProduct(ProductRequest productRequest) {
        SecurityContext securityContext = SecurityContext.getContext();
        return productJpaPort.createProduct(productRequest);
    }

    @Override
    public List<ProductDomainModel> getAllAvailableProducts() {
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
    public ProductDomainModel markProductAsInactive(Long productId) {
        return null;
    }

    @Override
    public void deleteProductById(Long productId) {
        ProductDomainModel productDomain = productJpaPort.getProductById(productId);
        if (productDomain != null) {
            productJpaPort.deleteProductById(productId);
        }
    }
}
