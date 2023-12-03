package huberts.spring.basket.adapter.out.feign.product;

import huberts.spring.basket.adapter.out.feign.product.model.ProductDomainModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@RestController
@ActiveProfiles("test")
public class MockProductServiceConfig {

    @RequestMapping("/api/product/{productId}")
    public ProductDomainModel getProduct(@PathVariable Long productId) {
        return new ProductDomainModel();
    }
}