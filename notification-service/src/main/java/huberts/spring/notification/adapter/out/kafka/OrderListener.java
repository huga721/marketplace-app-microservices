package huberts.spring.notification.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.notification.adapter.out.kafka.model.OrderModel;
import huberts.spring.notification.domain.port.in.NotificationServicePort;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class OrderListener {

    private final NotificationServicePort notificationServicePort;
    private final Logger LOGGER = LoggerFactory.getLogger(OrderListener.class);
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order.create.notification", groupId = "notification")
    public void listenOrderCreatedEvent(final String in) throws JsonProcessingException {
        LOGGER.info(">> OrderListener: caught event on topic = topic.create.notification {}", in);
        OrderModel order = objectMapper.readValue(in, OrderModel.class);
        String keycloakId = order.keycloakId();
        String message = "Order with id: " + order.id() + " was created.";
        notificationServicePort.createNotification(message, keycloakId);
    }
}