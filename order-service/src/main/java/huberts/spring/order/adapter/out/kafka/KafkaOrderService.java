package huberts.spring.order.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.order.adapter.out.feign.basket.model.BasketDomainModel;
import huberts.spring.order.domain.port.in.KafkaServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaOrderService implements KafkaServicePort {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendBasketInactiveEvent(BasketDomainModel basketDomainModel) {
        try {
            final String payload = objectMapper.writeValueAsString(basketDomainModel);
            kafkaTemplate.send("order.completed", payload);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
