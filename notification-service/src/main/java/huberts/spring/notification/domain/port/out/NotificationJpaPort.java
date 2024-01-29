package huberts.spring.notification.domain.port.out;

import huberts.spring.notification.domain.model.NotificationDomainModel;

import java.util.List;

public interface NotificationJpaPort {
    NotificationDomainModel saveNotification(NotificationDomainModel notificationDomainModel);
    List<NotificationDomainModel> getAllNotifications();
    NotificationDomainModel getNotificationById(Long notificationId);
}