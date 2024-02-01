package huberts.spring.notification.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.notification.adapter.out.kafka.model.ProductModel;
import huberts.spring.notification.domain.port.in.NotificationServicePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductListener {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductListener.class);
    private final ObjectMapper objectMapper;
    private final NotificationServicePort notificationServicePort;

    @KafkaListener(topics = "product.created.notification", groupId = "notification")
    public void listenProductCreatedEvent(final String in) throws JsonProcessingException {
        LOGGER.info(">> ProductListener: caught event on topic = topic.created.notification {}", in);
        ProductModel product = objectMapper.readValue(in, ProductModel.class);
        String keycloakId = product.keycloakId();
        String message = "Product with id: " + product.id() + " was created.";
        notificationServicePort.createNotification(message, keycloakId);
    }

    @KafkaListener(topics = "product.inactive.notification", groupId = "notification")
    public void listenProductInactiveEvent(final String in) throws JsonProcessingException {
        LOGGER.info(">> ProductListener: caught event on topic = topic.created.notification {}", in);
        List<ProductModel> products = objectMapper.readValue(in, new TypeReference<>() {});

        products.forEach(product -> {
            String keycloakId = product.keycloakId();
            String message = "Product with id: " + product.id() + " was set to inactive due to sold of item.";
            notificationServicePort.createNotification(message, keycloakId);
        });
    }

    @KafkaListener(topics = "product.delete.notification", groupId = "notification")
    public void listenProductDeleteEvent(final String in) throws JsonProcessingException {
        LOGGER.info(">> ProductListener: caught event on topic = topic.delete.notification {}", in);
        ProductModel product = objectMapper.readValue(in, ProductModel.class);
        String keycloakId = product.keycloakId();
        String message = "Product with id: " + product.id() + " was deleted.";
        notificationServicePort.createNotification(message, keycloakId);
    }

    @KafkaListener(topics = "product.edit.notification", groupId = "notification")
    public void listenProductEditEvent(final String in) throws JsonProcessingException {
        LOGGER.info(">> ProductListener: caught event on topic = topic.edit.notification {}", in);
        ProductModel product = objectMapper.readValue(in, ProductModel.class);
        String keycloakId = product.keycloakId();
        String message = "Product with id: " + product.id() + " was edited.";
        notificationServicePort.createNotification(message, keycloakId);
    }
}