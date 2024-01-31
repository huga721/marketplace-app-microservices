package huberts.spring.order.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.order.domain.model.OrderDomainModel;
import huberts.spring.order.domain.port.in.KafkaNotificationServicePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaNotificationService implements KafkaNotificationServicePort {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaBasketService.class);
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendOrderCreateNotificationEvent(OrderDomainModel orderDomainModel) {
        try {
            LOGGER.info(">> KafkaOrderService: attempt on sending event topic: order.complete");
            final String payload = objectMapper.writeValueAsString(orderDomainModel);
            kafkaTemplate.send("order.create.notification", payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
