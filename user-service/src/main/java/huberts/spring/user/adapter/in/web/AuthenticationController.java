package huberts.spring.user.adapter.in.web;

import huberts.spring.user.adapter.in.web.resource.CreateRequest;
import huberts.spring.user.adapter.in.web.resource.LoginRequest;
import huberts.spring.user.domain.model.UserDomainModel;
import huberts.spring.user.domain.port.in.KeycloakServicePort;
import huberts.spring.user.domain.port.in.UserServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Authentication REST API")
@Validated
@RestController
@RequestMapping("/api/user/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private final KeycloakServicePort keycloakServicePort;
    private final UserServicePort userServicePort;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    UserDomainModel createUser(@Valid @RequestBody CreateRequest createRequest) {
        LOGGER.info(">> AuthenticationController call: creating  user {}", createRequest);
        return userServicePort.createUser(createRequest);
    }

    @PostMapping("/login")
    AccessTokenResponse getToken(@Valid @RequestBody LoginRequest loginRequest) {
        LOGGER.info(">> AuthenticationController call: getting token {}", loginRequest);
        return keycloakServicePort.getToken(loginRequest);
    }
}