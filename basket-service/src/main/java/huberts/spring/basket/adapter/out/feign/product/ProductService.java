package huberts.spring.basket.adapter.out.feign.product;

import huberts.spring.basket.adapter.out.feign.product.ProductFeignClient;
import huberts.spring.basket.domain.port.ProductServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductService implements ProductServicePort {

    private final ProductFeignClient productFeignClient;

    @Override
    public Long getProductId(Long id) {
        return productFeignClient.getProductById(id);
    }
}
