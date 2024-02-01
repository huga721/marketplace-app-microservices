package huberts.spring.basket.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.basket.domain.port.in.KafkaProductServicePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaProductService implements KafkaProductServicePort {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProductService.class);
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendProductSoldEvent(List<Long> productsId) {
        try {
            LOGGER.info(">> KafkaProductService: attempt on sending event topic: product.sell");
            final String payload = objectMapper.writeValueAsString(productsId);
            kafkaTemplate.send("product.sell", payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}