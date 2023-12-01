package huberts.spring.basket.adapter.out.feign.product;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import huberts.spring.basket.adapter.out.feign.product.model.ProductDomainModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest({"server.port:0", "eureka.client.enabled:false"})
class ProductFeignClientIntegrationTest {

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public ServiceInstanceListSupplier serviceInstanceListSupplier() {
            return new TestServiceInstanceListSupplier("product-service", 8081);
        }
    }

    @RegisterExtension
    static WireMockExtension PRODUCT_SERVICE =
            WireMockExtension.newInstance()
                    .options(WireMockConfiguration
                            .wireMockConfig()
                            .port(8081)).build();

    @Autowired
    private ProductFeignClient productFeignClient;

    @Test
    @WithJwt("/keycloak/speedy.json")
    public void shouldReturnProductModel_WhenRequestedInFeignClient() {
        String responseBody = "{ \"id\": \"1\", \"name\": \"iPhone 12\", \"description\": \"description\"}";
        PRODUCT_SERVICE.stubFor(WireMock.get("/api/product/1").willReturn(WireMock.okJson(responseBody)));

        ProductDomainModel product = productFeignClient.getProductById(1L);

        assertNotNull(product);
        assertEquals(product.getId(), 1);
        assertEquals(product.getName(), "iPhone 12");
        assertEquals(product.getDescription(), "description");
    }
}