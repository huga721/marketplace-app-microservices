package huberts.spring.basket.adapter.out.feign.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "product-service", url = "http://localhost:8080")
public interface ProductFeignClient {

    @GetMapping("/{productId}")
    Long getProductById(@PathVariable Long productId);
}
