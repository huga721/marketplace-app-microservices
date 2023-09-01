package huberts.spring.authentication.application;

import huberts.spring.authentication.adapter.in.web.resource.UserRequest;
import huberts.spring.authentication.adapter.out.keycloak.KeycloakService;
import huberts.spring.authentication.common.exception.UserDoesntExistException;
import huberts.spring.authentication.domain.model.UserDomainModel;
import huberts.spring.authentication.domain.port.in.UserServicePort;
import huberts.spring.authentication.domain.port.out.UserJpaPort;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserServicePort {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserJpaPort userJpaPort;

    private final KeycloakService keycloakService;

    @Override
    public UserDomainModel createUser(UserRequest userRequest) {
        LOGGER.info("[UserService] createUser {}", userRequest);

        Response keycloakRegisterResponse = keycloakService.createUser(userRequest);
        String keycloakId = CreatedResponseUtil.getCreatedId(keycloakRegisterResponse);

        if (keycloakRegisterResponse.getStatus() == 201) {
            return userJpaPort.createUser(userRequest, keycloakId);
        }

        String errorMessage = String.format("User with username = %s already exist", userRequest.username());
        LOGGER.warn("An exception occurred!", new UserDoesntExistException(errorMessage));
        throw new UserDoesntExistException(errorMessage);
    }

    @Override
    public List<UserDomainModel> getAllUsers() {
        return userJpaPort.getAllUsers();
    }

    @Override
    public UserDomainModel getUserById(Long userId) {
        return userJpaPort.getUserById(userId);
    }

    @Override
    public UserDomainModel updateUserById(Long userId, UserRequest userRequest) {
        UserDomainModel user = userJpaPort.getUserById(userId);

        user.updateUser(userRequest);

        keycloakService.updateUser(
                user.getKeycloakId(),
                userRequest
        );
        return userJpaPort.updateUserById(user);
    }

    @Override
    public void deleteUserById(Long userId) {

    }

}