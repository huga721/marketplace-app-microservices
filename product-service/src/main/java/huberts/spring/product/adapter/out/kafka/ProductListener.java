package huberts.spring.product.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.product.domain.model.ProductDomainModel;
import huberts.spring.product.domain.port.in.ProductServicePort;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
@Getter
@RequiredArgsConstructor
public class ProductListener {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductListener.class);

    private final ObjectMapper objectMapper;
    private final ProductServicePort productServicePort;

    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "product.sold", groupId = "one")
    public List<ProductDomainModel> listens(final String in) throws JsonProcessingException {
        latch.countDown();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        List<Long> productsId = objectMapper.readValue(in, new TypeReference<List<Long>>() {});

        LOGGER.info(">> Products id received from product.sold topic: {}", productsId);
        List<ProductDomainModel> products = productServicePort.setProductsAsInactive(productsId);

        LOGGER.info(">> Products that has been changed to inactive: {}", products);
        return products;
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
}