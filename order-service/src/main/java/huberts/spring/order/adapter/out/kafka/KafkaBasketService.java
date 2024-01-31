package huberts.spring.order.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.order.adapter.out.feign.basket.model.BasketDomainModel;
import huberts.spring.order.domain.port.in.KafkaBasketServicePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaBasketService implements KafkaBasketServicePort {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaBasketService.class);
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendBasketInactiveEvent(BasketDomainModel basketDomainModel) {
        try {
            LOGGER.info(">> KafkaOrderService: attempt on sending event topic: order.complete");
            final String payload = objectMapper.writeValueAsString(basketDomainModel);
            kafkaTemplate.send("order.complete", payload);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}