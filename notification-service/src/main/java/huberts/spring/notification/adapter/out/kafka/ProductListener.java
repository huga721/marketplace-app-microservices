package huberts.spring.notification.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.notification.adapter.out.kafka.model.ProductModel;
import huberts.spring.notification.domain.port.in.NotificationServicePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductListener {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductListener.class);
    private final ObjectMapper objectMapper;
    private final NotificationServicePort notificationServicePort;

    @KafkaListener(topics = "topic.created.notification", groupId = "one")
    public void listenProductCreatedEvent(final String in) throws JsonProcessingException {
        LOGGER.info(">> ProductListener: caught event on topic = topic.created.notification {}", in);
        ProductModel product = objectMapper.readValue(in, ProductModel.class);
        String keycloakId = product.keycloakId();
        String message = "Product with id: " + product.id() + " was created.";
        notificationServicePort.createNotification(message, keycloakId);
    }
}