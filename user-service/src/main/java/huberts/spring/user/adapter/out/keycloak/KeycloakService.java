package huberts.spring.user.adapter.out.keycloak;

import huberts.spring.user.adapter.in.web.resource.EditRequest;
import huberts.spring.user.adapter.in.web.resource.LoginRequest;
import huberts.spring.user.adapter.in.web.resource.CreateRequest;
import huberts.spring.user.application.exception.UserExistException;
import huberts.spring.user.domain.port.in.KeycloakServicePort;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService implements KeycloakServicePort {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${keycloak.server-url}")
    private String SERVER_URL;
    @Value("${keycloak.realm}")
    private String REALM;
    @Value("${keycloak.client-id}")
    private String CLIENT_ID;
    @Value("${keycloak.client-secret}")
    private String CLIENT_SECRET;

    private final Keycloak keycloak;

    @Override
    public Response createUser(CreateRequest createRequest) {
        LOGGER.info(">> KeycloakService: request for creating user: {}", createRequest);

        UserRepresentation userRepresentation = buildUserRepresentation(createRequest);
        CredentialRepresentation credentialRepresentation = buildCredentialRepresentation(createRequest.password());

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        UsersResource userResource = keycloak.realm(REALM).users();

        Response response = userResource.create(userRepresentation);

        if (response.getStatus() == 409) {
            LOGGER.warn("Creating user in KeycloakService interrupted. User already exist.");
            throw new UserExistException("User already exist.");
        }

        String userId = CreatedResponseUtil.getCreatedId(response);

        RoleRepresentation savedRoleRepresentation = keycloak.realm(REALM).roles()
                .get("user").toRepresentation();

        keycloak.realm(REALM).users().get(userId).roles().realmLevel()
                .add(Arrays.asList(savedRoleRepresentation));

        return response;
    }

    private UserRepresentation buildUserRepresentation(CreateRequest userRequest) {
        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setUsername(userRequest.username());
        userRepresentation.setFirstName(userRequest.firstName());
        userRepresentation.setLastName(userRequest.lastName());
        userRepresentation.setEmail(userRequest.email());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        HashMap<String, List<String>> userRoles = new HashMap<>();
        userRoles.put(CLIENT_ID, Collections.singletonList("ROLE_USER"));
        userRepresentation.setClientRoles(userRoles);

        return userRepresentation;
    }

    private CredentialRepresentation buildCredentialRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(password);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        return credentialRepresentation;
    }

    @Override
    public AccessTokenResponse getToken(LoginRequest loginRequest) {
        LOGGER.info(">> KeycloakService: getting token by: {}", loginRequest);
        Keycloak keycloakClient = buildKeycloakClient(loginRequest);

        return keycloakClient.tokenManager().getAccessToken();
    }

    @Override
    public void updateUser(String keycloakId, EditRequest editRequest) {
        LOGGER.info(">> KeycloakService: updating: {} user with keycloak id: {}", editRequest, keycloakId);

        RealmResource realmResource = keycloak.realm(REALM);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(keycloakId);

        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.setUsername(editRequest.username());
        userRepresentation.setFirstName(editRequest.firstName());
        userRepresentation.setLastName(editRequest.lastName());
        userRepresentation.setEmail(editRequest.email());

        userResource.update(userRepresentation);
    }

    @Override
    public void deleteUser(String keycloakId) {
        LOGGER.info(">> KeycloakService: deleting user with keycloak id: {}", keycloakId);

        RealmResource realmResource = keycloak.realm(REALM);

        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(keycloakId);

        userResource.remove();
    }

    private Keycloak buildKeycloakClient(LoginRequest loginRequest) {
        return KeycloakBuilder.builder()
                .realm(REALM)
                .serverUrl(SERVER_URL)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .username(loginRequest.username())
                .password(loginRequest.password())
                .build();
    }
}
