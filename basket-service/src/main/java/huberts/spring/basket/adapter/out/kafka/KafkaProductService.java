package huberts.spring.basket.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.basket.domain.port.in.KafkaServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaProductService implements KafkaServicePort {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendProductSoldEvent(List<Long> productsId) {
        try {
            final String payload = objectMapper.writeValueAsString(productsId);
            kafkaTemplate.send("product.sold", payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}