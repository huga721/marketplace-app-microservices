package huberts.spring.authentication.adapter.out.persistance;

import huberts.spring.authentication.adapter.in.web.resource.UserRequest;
import huberts.spring.authentication.adapter.out.persistance.entity.UserEntity;
import huberts.spring.authentication.adapter.out.persistance.repository.UserRepository;
import huberts.spring.authentication.common.exception.UserDoesntExistException;
import huberts.spring.authentication.domain.model.UserDomainModel;
import huberts.spring.authentication.domain.port.out.UserJpaPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static huberts.spring.authentication.adapter.out.persistance.mapper.UserJpaMapper.USER_JPA_MAPPER;

@Component
@RequiredArgsConstructor
class UserJpaAdapter implements UserJpaPort {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    @Override
    public UserDomainModel createUser(UserRequest userRequest, String keycloakId) {
        UserEntity userEntity = USER_JPA_MAPPER.toJpaEntity(userRequest, keycloakId);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return USER_JPA_MAPPER.toDomainModel(savedUserEntity);
    }

    @Override
    public List<UserDomainModel> getAllUsers() {
        return userRepository.findAll().stream()
                .map(USER_JPA_MAPPER::toDomainModel)
                .toList();
    }

    @Override
    public UserDomainModel getUserById(Long userId) {
        return USER_JPA_MAPPER.toDomainModel(findById(userId));
    }

    private UserEntity findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("User with id = %s doesn't exist", userId);
                    LOGGER.warn("An exception occurred!", new UserDoesntExistException(errorMessage));
                    return new UserDoesntExistException(errorMessage);
                });
    }

    @Override
    public UserDomainModel updateUserById(UserDomainModel userDomainModel) {
        UserEntity userEntity = USER_JPA_MAPPER.toUpdatedJpaEntity(userDomainModel);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return USER_JPA_MAPPER.toDomainModel(savedUserEntity);
    }

    @Override
    public void deleteUserById(Long userId) {
        UserEntity userEntity = findById(userId);
        userRepository.delete(userEntity);
    }

    @Override
    public boolean existUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
