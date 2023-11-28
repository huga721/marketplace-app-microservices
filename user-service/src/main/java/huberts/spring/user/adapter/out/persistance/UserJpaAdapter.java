package huberts.spring.user.adapter.out.persistance;

import huberts.spring.user.adapter.in.web.resource.CreateRequest;
import huberts.spring.user.adapter.out.persistance.entity.UserEntity;
import huberts.spring.user.adapter.out.persistance.repository.UserRepository;
import huberts.spring.user.application.exception.UserNotFoundException;
import huberts.spring.user.domain.model.UserDomainModel;
import huberts.spring.user.domain.port.out.UserJpaPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static huberts.spring.user.adapter.out.persistance.mapper.UserJpaMapper.USER_JPA_MAPPER;

@Service
@RequiredArgsConstructor
public class UserJpaAdapter implements UserJpaPort {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    @Override
    public UserDomainModel createUser(CreateRequest userRequest, String keycloakId) {
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

    @Override
    public UserDomainModel getUserByKeycloakId(String keycloakId) {
        return USER_JPA_MAPPER.toDomainModel(findByKeycloakId(keycloakId));
    }

    private UserEntity findByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("User with keycloakId = %s doesn't exist", keycloakId);
                    LOGGER.warn("An exception occurred!", new UserNotFoundException(errorMessage));
                    return new UserNotFoundException(errorMessage);
                });
    }

    private UserEntity findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("User with id = %s doesn't exist", userId);
                    LOGGER.warn("An exception occurred!", new UserNotFoundException(errorMessage));
                    return new UserNotFoundException(errorMessage);
                });
    }

    @Override
    public void deleteUser(UserDomainModel userDomainModel) {
        UserEntity user = USER_JPA_MAPPER.toUpdatedJpaEntity(userDomainModel);
        userRepository.delete(user);
    }
}
