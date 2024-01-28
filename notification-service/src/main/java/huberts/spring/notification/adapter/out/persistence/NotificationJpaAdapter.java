package huberts.spring.notification.adapter.out.persistence;

import huberts.spring.notification.adapter.out.persistence.entity.NotificationEntity;
import huberts.spring.notification.adapter.out.persistence.exception.NotificationNotFoundException;
import huberts.spring.notification.adapter.out.persistence.repository.NotificationRepository;
import huberts.spring.notification.domain.model.NotificationDomainModel;
import huberts.spring.notification.domain.port.out.NotificationJpaPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static huberts.spring.notification.adapter.out.persistence.mapper.NotificationJpaMapper.NOTIFICATION_JPA_MAPPER;

@Component
@RequiredArgsConstructor
public class NotificationJpaAdapter implements NotificationJpaPort {

    private final Logger LOGGER = LoggerFactory.getLogger(NotificationJpaAdapter.class);
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationDomainModel saveNotification(NotificationDomainModel notificationDomainModel) {
        NotificationEntity notificationEntity = NOTIFICATION_JPA_MAPPER.toJpaEntity(notificationDomainModel);
        NotificationEntity notificationSaved = notificationRepository.save(notificationEntity);
        return NOTIFICATION_JPA_MAPPER.toDomainModel(notificationSaved);
    }

    @Override
    public List<NotificationDomainModel> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(NOTIFICATION_JPA_MAPPER::toDomainModel)
                .toList();
    }

    @Override
    public NotificationDomainModel getNotificationById(Long notificationId) {
        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Notification with id = %s doesn't exist.", notificationId);
                    LOGGER.warn("An exception occurred!", new NotificationNotFoundException(errorMessage));
                    return new NotificationNotFoundException(errorMessage);
                });
        return NOTIFICATION_JPA_MAPPER.toDomainModel(notification);
    }
}