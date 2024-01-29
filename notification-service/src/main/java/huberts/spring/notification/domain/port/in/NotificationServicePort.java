package huberts.spring.notification.domain.port.in;

import huberts.spring.notification.domain.model.NotificationDomainModel;

import java.util.List;

public interface NotificationServicePort {
    NotificationDomainModel createNotification(String message, String keycloakId);

    List<NotificationDomainModel> getAllNotifications();
    NotificationDomainModel getNotificationById(Long notificationId);
}