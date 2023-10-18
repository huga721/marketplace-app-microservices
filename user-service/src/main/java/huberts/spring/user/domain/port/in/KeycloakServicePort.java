package huberts.spring.user.domain.port.in;

import huberts.spring.user.adapter.in.web.resource.LoginRequest;
import huberts.spring.user.adapter.in.web.resource.UserRequest;
import jakarta.ws.rs.core.Response;
import org.keycloak.representations.AccessTokenResponse;

public interface KeycloakServicePort {
    Response createUser(UserRequest userRequest);

    AccessTokenResponse getToken(LoginRequest loginRequest);

    void updateUser(String keycloakId, UserRequest userRequest);
}
