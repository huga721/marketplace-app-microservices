package huberts.spring.user.adapter.out.persistance.mapper;

import huberts.spring.user.adapter.in.web.resource.UserRequest;
import huberts.spring.user.adapter.out.persistance.entity.UserEntity;
import huberts.spring.user.domain.model.UserDomainModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserJpaMapper {

    UserJpaMapper USER_JPA_MAPPER = Mappers.getMapper(UserJpaMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roleName", constant = "ROLE_USER")
    UserEntity toJpaEntity(UserRequest userRequest, String keycloakId);

    UserEntity toUpdatedJpaEntity(UserDomainModel userDomainModel);

    UserDomainModel toDomainModel(UserEntity userEntity);
}
