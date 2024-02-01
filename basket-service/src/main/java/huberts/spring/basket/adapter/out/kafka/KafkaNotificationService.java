package huberts.spring.basket.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.port.in.KafkaNotificationServicePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaNotificationService implements KafkaNotificationServicePort {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaNotificationService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private void sendNotification(String topic, Object payload) {
        try {
            LOGGER.info(">> KafkaNotificationService: attempt on sending event topic: {}", topic);
            final String serializedPayload = objectMapper.writeValueAsString(payload);
            kafkaTemplate.send(topic, serializedPayload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendBasketCreateNotificationEvent(BasketDomainModel basketDomainModel) {
        sendNotification("basket.create.notification", basketDomainModel);
    }

    @Override
    public void sendBasketAddProductNotificationEvent(BasketDomainModel basketDomainModel) {
        sendNotification("basket.add.product.notification", basketDomainModel);
    }

    @Override
    public void sendBasketRemoveProductNotificationEvent(BasketDomainModel basketDomainModel) {
        sendNotification("basket.remove.product.notification", basketDomainModel);
    }

    @Override
    public void sendBasketInactiveNotificationEvent(BasketDomainModel basketDomainModel) {
        sendNotification("basket.inactive.notification", basketDomainModel);
    }
}
