package huberts.spring.basket.domain.port.in;

import huberts.spring.basket.adapter.out.feign.product.model.ProductDomainModel;

public interface ProductServicePort {

    ProductDomainModel findProduct(Long id);

}
