package huberts.spring.user.adapter.in.web;

import huberts.spring.user.adapter.in.web.resource.CreateRequest;
import huberts.spring.user.adapter.in.web.resource.LoginRequest;
import huberts.spring.user.domain.model.UserDomainModel;
import huberts.spring.user.domain.port.in.KeycloakServicePort;
import huberts.spring.user.domain.port.in.UserServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Authentication REST API")
@RestController
@RequestMapping("/api/user/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final KeycloakServicePort keycloakServicePort;
    private final UserServicePort userServicePort;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    UserDomainModel createUser(@RequestBody CreateRequest userRequest) {
        return userServicePort.createUser(userRequest);
    }

    @PostMapping("/login")
    AccessTokenResponse getToken(@RequestBody LoginRequest loginRequest) {
        return keycloakServicePort.getToken(loginRequest);
    }
}