package huberts.spring.notification.adapter.out.persistence.mapper;

import huberts.spring.notification.adapter.out.persistence.entity.NotificationEntity;
import huberts.spring.notification.domain.model.NotificationDomainModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationJpaMapper {

    NotificationJpaMapper NOTIFICATION_JPA_MAPPER = Mappers.getMapper(NotificationJpaMapper.class);

    NotificationEntity toJpaEntity(NotificationDomainModel notificationDomainModel);

    NotificationDomainModel toDomainModel(NotificationEntity notificationEntity);
}