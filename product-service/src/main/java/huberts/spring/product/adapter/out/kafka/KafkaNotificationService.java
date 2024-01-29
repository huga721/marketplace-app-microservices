package huberts.spring.product.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.product.domain.model.ProductDomainModel;
import huberts.spring.product.domain.port.in.KafkaNotificationServicePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaNotificationService implements KafkaNotificationServicePort {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(KafkaNotificationService.class);

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
    public void sendProductCreatedNotificationEvent(ProductDomainModel productDomainModel) {
        sendNotification("topic.created.notification", productDomainModel);
    }

    @Override
    public void sendProductInactiveNotificationEvent(List<ProductDomainModel> productDomainModel) {
        sendNotification("topic.inactive.notification", productDomainModel);
    }

    @Override
    public void sendProductEditedNotificationEvent(ProductDomainModel productDomainModel) {
        sendNotification("topic.edit.notification", productDomainModel);
    }

    @Override
    public void sendProductDeletedNotificationEvent(ProductDomainModel productDomainModel) {
        sendNotification("topic.delete.notification", productDomainModel);
    }
}