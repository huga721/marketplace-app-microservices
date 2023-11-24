package huberts.spring.user.domain.port.in;

import huberts.spring.user.adapter.in.web.resource.CreateRequest;
import huberts.spring.user.adapter.in.web.resource.EditRequest;
import huberts.spring.user.adapter.in.web.resource.LoginRequest;
import jakarta.ws.rs.core.Response;
import org.keycloak.representations.AccessTokenResponse;

public interface KeycloakServicePort {
    Response createUser(CreateRequest userRequest);

    AccessTokenResponse getToken(LoginRequest loginRequest);

    void updateUser(String keycloakId, EditRequest editRequest);

    void deleteUser(String keycloakId);
}
