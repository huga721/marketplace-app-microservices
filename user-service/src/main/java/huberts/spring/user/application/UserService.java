package huberts.spring.user.application;

import huberts.spring.user.adapter.in.web.resource.CreateRequest;
import huberts.spring.user.adapter.in.web.resource.EditRequest;
import huberts.spring.user.application.exception.UserExistException;
import huberts.spring.user.domain.model.UserDomainModel;
import huberts.spring.user.domain.port.in.KeycloakServicePort;
import huberts.spring.user.domain.port.in.UserServicePort;
import huberts.spring.user.domain.port.out.UserJpaPort;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserServicePort {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserJpaPort userJpaPort;
    private final KeycloakServicePort keycloakServicePort;

    @Override
    public List<UserDomainModel> getAllUsers() {
        LOGGER.info("UserService: getting all users from jpa port");
        return userJpaPort.getAllUsers();
    }

    @Override
    public UserDomainModel getUserById(Long userId) {
        LOGGER.info("UserService: getting user with id {} from jpa port", userId);
        return userJpaPort.getUserById(userId);
    }

    @Override
    public UserDomainModel editUserByKeycloakId(String keycloakId, EditRequest editRequest) {
        LOGGER.info("UserService: editing user with keycloak id {} by edit request body {}", keycloakId, editRequest);

        UserDomainModel user = userJpaPort.getUserByKeycloakId(keycloakId);

        LOGGER.info("UserService: found user {} with keycloak id {}", user, keycloakId);
        user.updateUser(editRequest);

        keycloakServicePort.updateUser(keycloakId, editRequest);
        LOGGER.info("UserService: updated user {}", user);
        return user;
    }

    @Override
    public UserDomainModel createUser(CreateRequest createRequest) {
        LOGGER.info("UserService: creating user with create request {}", createRequest);

        Response keycloakRegisterResponse = keycloakServicePort.createUser(createRequest);

        LOGGER.info("UserService: response from keycloak {}", keycloakRegisterResponse.getStatus());

        if (keycloakRegisterResponse.getStatus() == 201) {
            String keycloakId = CreatedResponseUtil.getCreatedId(keycloakRegisterResponse);

            LOGGER.info("UserService: keycloak status 201, creating user");
            return userJpaPort.createUser(createRequest, keycloakId);
        }

        String errorMessage = String.format("User with username = %s already exist", createRequest.username());
        LOGGER.warn("An exception occurred!", new UserExistException(errorMessage));
        throw new UserExistException(errorMessage);
    }



    @Override
    public UserDomainModel editUserById(Long userId, EditRequest editRequest) {
        LOGGER.info("UserService: editing user with id {} by edit request body {}", userId, editRequest);

        UserDomainModel user = userJpaPort.getUserById(userId);

        LOGGER.info("UserService: found user {} with id {}", user.toString(), userId);

        user.updateUser(editRequest);

        keycloakServicePort.updateUser(user.getKeycloakId(), editRequest);
        LOGGER.info("UserService: updated user {}", user);
        return user;
    }

    @Override
    public void deleteUserByKeycloakId(String keycloakId) {
        LOGGER.info("UserService: deleting user with keycloak id: {}", keycloakId);
        UserDomainModel user = userJpaPort.getUserByKeycloakId(keycloakId);

        keycloakServicePort.deleteUser(keycloakId);

        userJpaPort.deleteUser(user);
        LOGGER.info("UserService: deleted user {}", user);
    }

    @Override
    public void deleteUserById(Long userId) {
        LOGGER.info("UserService: deleting user with id: {}", userId);
        UserDomainModel user = userJpaPort.getUserById(userId);

        keycloakServicePort.deleteUser(user.getKeycloakId());

        userJpaPort.deleteUser(user);
        LOGGER.info("UserService: deleted user {}", user);
    }
}