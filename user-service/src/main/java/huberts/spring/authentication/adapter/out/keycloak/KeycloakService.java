package huberts.spring.authentication.adapter.out.keycloak;

import huberts.spring.authentication.adapter.in.web.resource.LoginRequest;
import huberts.spring.authentication.adapter.in.web.resource.UserRequest;
import huberts.spring.authentication.domain.port.in.KeycloakServicePort;
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
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService implements KeycloakServicePort {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final static String SERVER_URL = "http://localhost:8181";
    private final static String REALM = "marketplace-app-realm";
    private final static String CLIENT_ID = "marketplace-app";
    private final static String CLIENT_SECRET = "5qV3OotxRqJ0M4Mj9t4xaD37gmkG8QAs";

    private final Keycloak keycloak;

    @Override
    public Response createUser(UserRequest userRequest) {
        LOGGER.info("[KeycloakService] createUser {}", userRequest);
        UserRepresentation userRepresentation = buildUserRepresentation(userRequest);
        CredentialRepresentation credentialRepresentation = buildCredentialRepresentation(userRequest.password());

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        UsersResource userResource = keycloak.realm(REALM).users();

        Response response = userResource.create(userRepresentation);

        String userId = CreatedResponseUtil.getCreatedId(response);

        RoleRepresentation savedRoleRepresentation = keycloak.realm(REALM).roles()
                .get("user").toRepresentation();

        keycloak.realm(REALM).users().get(userId).roles().realmLevel()
                .add(Arrays.asList(savedRoleRepresentation));

        return response;
    }

    private UserRepresentation buildUserRepresentation(UserRequest userRequest) {
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
        LOGGER.info("[KeycloakService] getToken {}", loginRequest);
        Keycloak keycloakClient = buildKeycloakClient(loginRequest);
        return keycloakClient.tokenManager().getAccessToken();
    }

    @Override
    public void updateUser(String keycloakId, UserRequest userRequest) {
        RealmResource realmResource = keycloak.realm(REALM);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(keycloakId);

        UserRepresentation userRepresentation = buildUserRepresentation(userRequest);
        userResource.update(userRepresentation);

        CredentialRepresentation credential = buildCredentialRepresentation(userRequest.password());
        userResource.resetPassword(credential);
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
