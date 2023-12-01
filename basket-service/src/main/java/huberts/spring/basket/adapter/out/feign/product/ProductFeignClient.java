package huberts.spring.basket.adapter.out.feign.product;

import huberts.spring.basket.adapter.out.feign.product.model.ProductDomainModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "product-service",
        configuration = FeignClientConfiguration.class)
public interface ProductFeignClient {

    @GetMapping(
            value = "/api/product/{productId}",
            consumes = "application/json",
            produces = "application/json")
    ProductDomainModel getProductById(@PathVariable("productId") Long productId);
}
