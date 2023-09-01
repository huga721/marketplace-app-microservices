package huberts.spring.authentication.adapter.out.persistance.mapper;

import huberts.spring.authentication.adapter.in.web.resource.UserRequest;
import huberts.spring.authentication.adapter.out.persistance.entity.UserEntity;
import huberts.spring.authentication.domain.model.UserDomainModel;
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
