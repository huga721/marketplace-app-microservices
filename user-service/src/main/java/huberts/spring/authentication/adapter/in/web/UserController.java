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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "users", description = "User operations")
@RestController
@RequiredArgsConstructor
class UserController {

    private final UserServicePort userServicePort;
    private final KeycloakServicePort keycloakServicePort;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    UserDomainModel createUserKeycloak(@RequestBody UserRequest userRequest) {
        return userServicePort.createUser(userRequest);
    }

    @PostMapping("/login")
    AccessTokenResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return keycloakServicePort.getToken(loginRequest);
    }

    @GetMapping
    List<UserDomainModel> getAllUsers() {
        return userServicePort.getAllUsers();
    }

    @GetMapping("/{userId}")
    @RolesAllowed("role-user")
    UserDomainModel getUserById(@PathVariable Long userId) {
        return userServicePort.getUserById(userId);
    }

    @GetMapping("/user-info")
    @RolesAllowed("role-user")
    UserDomainModel getUserInfo() {
        return null;
    }

    @PatchMapping("/{userId}")
    @RolesAllowed("role-admin")
    UserDomainModel updateUserById(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        return userServicePort.updateUserById(userId, userRequest);
    }

    @DeleteMapping("/{userId}")
    @RolesAllowed("role-admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserById(@PathVariable Long userId) {
        userServicePort.deleteUserById(userId);
    }
}