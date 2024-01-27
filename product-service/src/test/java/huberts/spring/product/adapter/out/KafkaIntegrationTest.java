package huberts.spring.product.adapter.in.web.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.product.adapter.out.kafka.ProductListener;
import huberts.spring.product.adapter.out.persistance.entity.ProductEntity;
import huberts.spring.product.adapter.out.persistance.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static huberts.spring.product.adapter.out.persistance.entity.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092"})
public class KafkaIntegrationTest {

    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ProductListener listener;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReceiveMessageFromKafkaBroker() throws JsonProcessingException, InterruptedException {
        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setName("Product");
        product.setDescription("dawd");
        product.setStatus(ACTIVE);
        productRepository.save(product);

        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        List<Long> longList = new ArrayList<>();
        longList.add(1L);

        String productsAsString = objectMapper.writeValueAsString(longList);

        kafkaTemplate.send("product.sold", productsAsString);

        boolean messageConsumed = listener.getLatch().await(10, TimeUnit.SECONDS);

        assertTrue(messageConsumed);
    }
}