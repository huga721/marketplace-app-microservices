package huberts.spring.notification.adapter.out.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class BasketListener {

    private final NotificationServicePort notificationServicePort;

    private final Logger LOGGER = LoggerFactory.getLogger(BasketListener.class);
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order.notification", groupId = "one")
    public String listens(final String in) {
        LOGGER.info(">> KafkaListener: received message from topic = order.notification");
//        notificationServicePort.createNotification(in);
        return in;
    }
}
