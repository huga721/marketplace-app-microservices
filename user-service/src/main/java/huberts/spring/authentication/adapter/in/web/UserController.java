package huberts.spring.authentication.adapter.in.web;

import huberts.spring.authentication.adapter.in.web.resource.LoginRequest;
import huberts.spring.authentication.adapter.in.web.resource.UserRequest;
import huberts.spring.authentication.domain.model.UserDomainModel;
import huberts.spring.authentication.domain.port.in.KeycloakServicePort;
import huberts.spring.authentication.domain.port.in.UserServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "users", description = "User operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserServicePort userServicePort;
    private final KeycloakServicePort keycloakServicePort;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    UserDomainModel createUserKeycloak(@RequestBody UserRequest userRequest) {
        LOGGER.info("[UserController] PostMapping /api/register {}", userRequest);
        return userServicePort.createUser(userRequest);
    }

    @PostMapping("/login")
    AccessTokenResponse loginUser(@RequestBody LoginRequest loginRequest) {
        LOGGER.info("[UserController] PostMapping /api/login {}", loginRequest);
        return keycloakServicePort.getToken(loginRequest);
    }

    @GetMapping
    List<UserDomainModel> getAllUsers() {
        LOGGER.info("[UserController] GetMapping /api");
        return userServicePort.getAllUsers();
    }

    @GetMapping("/{userId}")
    @RolesAllowed("role-user")
    UserDomainModel getUserById(@PathVariable Long userId) {
        LOGGER.info("[UserController] GetMapping /api/{}", userId);
        return userServicePort.getUserById(userId);
    }

    @GetMapping("/user-info")
    @RolesAllowed("role-user")
    UserDomainModel getUserInfo() {
        LOGGER.info("[UserController] GetMapping /api/user-info");
        return userServicePort.
    }

    @PatchMapping("/{userId}")
    @RolesAllowed("role-admin")
    UserDomainModel updateUserById(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        LOGGER.info("[UserController] PatchMapping /api/{} {}", userId, userRequest);
        return userServicePort.updateUserById(userId, userRequest);
    }

    @DeleteMapping("/{userId}")
    @RolesAllowed("role-admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserById(@PathVariable Long userId) {
        LOGGER.info("[UserController] DeleteMapping /api/{}", userId);
        userServicePort.deleteUserById(userId);
    }

    @RolesAllowed({"role-user"})
    @GetMapping("/XD")
    String aha() {
        return "Co tu nie działa?";
    }
}