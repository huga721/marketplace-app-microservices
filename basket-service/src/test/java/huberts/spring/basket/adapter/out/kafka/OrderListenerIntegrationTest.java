package huberts.spring.basket.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.basket.adapter.out.feign.product.model.Status;
import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import huberts.spring.basket.adapter.out.persistance.entity.BasketProductEntity;
import huberts.spring.basket.adapter.out.persistance.repository.BasketProductRepository;
import huberts.spring.basket.adapter.out.persistance.repository.BasketRepository;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.port.in.BasketServicePort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderListenerIntegrationTest {

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    static {
        kafkaContainer.start();
    }

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.bootstrap-server", kafkaContainer::getBootstrapServers);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketProductRepository basketProductRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BasketServicePort basketServicePort;

    private BasketEntity basket;
    private BasketProductEntity product;

    @BeforeAll
    public void init() {
        basket = new BasketEntity();
        product = new BasketProductEntity();

        product.setId(1L);
        product.setProductId(1L);
        product.setProductValue(50L);
        basket.setId(1L);
        basket.setKeycloakId("e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88");
        basket.setBasketValue(product.getProductValue());
        product.setBasketEntity(basket);
        basket.setProductNumber(1);

        basketRepository.save(basket);
        basketProductRepository.save(product);
        basketRepository.save(basket);
    }

    @Test
    void shouldSetBasketAsInactive() throws JsonProcessingException {
        BasketDomainModel basketDomainModel = basketServicePort.getBasketById(1L);
        String in = objectMapper.writeValueAsString(basketDomainModel);
        kafkaTemplate.send("order.completed", in);

        await().pollInterval(Duration.ofSeconds(3)).atMost(10, SECONDS).untilAsserted(() -> {
            BasketDomainModel basketDomainModel1 = basketServicePort.getBasketById(1L);
            assertNotNull(basketDomainModel1);
            assertEquals(basketDomainModel1.getStatus(), Status.INACTIVE);
        });
    }
}