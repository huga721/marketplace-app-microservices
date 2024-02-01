package huberts.spring.notification.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.notification.adapter.out.kafka.model.BasketModel;
import huberts.spring.notification.domain.port.in.NotificationServicePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasketListener {

    private final NotificationServicePort notificationServicePort;
    private final Logger LOGGER = LoggerFactory.getLogger(OrderListener.class);
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "basket.create.notification", groupId = "notification")
    public void listenBasketCreateEvent(final String in) throws JsonProcessingException {
        LOGGER.info(">> BasketListener: caught event on topic = basket.create.notification {}", in);
        BasketModel basket = objectMapper.readValue(in, BasketModel.class);
        String keycloakId = basket.keycloakId();
        String message = "Basket with id: " + basket.id() + " was created.";
        notificationServicePort.createNotification(message, keycloakId);
    }

    @KafkaListener(topics = "basket.add.product.notification", groupId = "notification")
    public void listenBasketAddProductEvent(final String in) throws JsonProcessingException {
        LOGGER.info(">> BasketListener: caught event on topic = basket.add.product.notification {}", in);
        BasketModel basket = objectMapper.readValue(in, BasketModel.class);
        String keycloakId = basket.keycloakId();

        int indexLastProduct = basket.basketProducts().size() - 1;
        Long productId = basket.basketProducts().get(indexLastProduct).id();

        String message = "Product with id: " + productId + " was added to basket with id: " + basket.id();
        notificationServicePort.createNotification(message, keycloakId);
    }

    @KafkaListener(topics = "basket.remove.product.notification", groupId = "notification")
    public void listenBasketRemoveProductEvent(final String in) throws JsonProcessingException {
        LOGGER.info(">> BasketListener: caught event on topic = basket.remove.product.notification {}", in);
        BasketModel basket = objectMapper.readValue(in, BasketModel.class);
        String keycloakId = basket.keycloakId();

        int indexLastProduct = basket.basketProducts().size() - 1;
        Long productId = basket.basketProducts().get(indexLastProduct).id();

        String message = "Product with id: " + productId + " was remove from basket with id: " + basket.id();
        notificationServicePort.createNotification(message, keycloakId);
    }

    @KafkaListener(topics = "basket.inactive.notification", groupId = "notification")
    public void listenBasketInactiveProductEvent(final String in) throws JsonProcessingException {
        LOGGER.info(">> BasketListener: caught event on topic = basket.inactive.notification {}", in);
        BasketModel basket = objectMapper.readValue(in, BasketModel.class);
        String keycloakId = basket.keycloakId();
        String message = "Basket with id: " + basket.id() + " is inactive.";
        notificationServicePort.createNotification(message, keycloakId);
    }
}
