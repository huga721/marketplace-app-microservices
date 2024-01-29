package huberts.spring.notification.application;

import huberts.spring.notification.domain.model.NotificationDomainModel;
import huberts.spring.notification.domain.port.in.NotificationServicePort;
import huberts.spring.notification.domain.port.out.NotificationJpaPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationServicePort {

    private final NotificationJpaPort notificationJpaPort;
    private final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    @Override
    public NotificationDomainModel createNotification(String message, String keycloakId) {
        LOGGER.info(">> NotificationService: creating a new notification with message: {}", message);
        NotificationDomainModel notification = new NotificationDomainModel();
        notification.setMessage(message);
        notification.setKeycloakId(keycloakId);
        notification.setTimestamp(LocalDateTime.now());
        LOGGER.info(">> NotificationService: new notification saved: {}", notification);

        NotificationDomainModel notificationSaved = notificationJpaPort.saveNotification(notification);
        LOGGER.info(">> NotificationService: new notification saved: {}", notificationSaved);
        return notificationSaved;
    }

    @Override
    public List<NotificationDomainModel> getAllNotifications() {
        LOGGER.info(">> NotificationService: getting all notifications");
        return notificationJpaPort.getAllNotifications();
    }

    @Override
    public NotificationDomainModel getNotificationById(Long notificationId) {
        LOGGER.info(">> NotificationService: getting notification with id: {}", notificationId);
        return notificationJpaPort.getNotificationById(notificationId);
    }
}