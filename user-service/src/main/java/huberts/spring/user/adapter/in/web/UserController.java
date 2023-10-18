package huberts.spring.user.adapter.in.web;

import huberts.spring.user.adapter.in.web.resource.LoginRequest;
import huberts.spring.user.adapter.in.web.resource.UserRequest;
import huberts.spring.user.domain.model.UserDomainModel;
import huberts.spring.user.domain.port.in.KeycloakServicePort;
import huberts.spring.user.domain.port.in.UserServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "User operations")
@RestController
@RequiredArgsConstructor
class UserController {

    private final UserServicePort userServicePort;

    @GetMapping
    List<UserDomainModel> getAllUsers() {
        return userServicePort.getAllUsers();
    }

    @GetMapping("/{userId}")
    @RolesAllowed("role-user")
    UserDomainModel getUserById(@PathVariable Long userId) {
        return userServicePort.getUserById(userId);
    }

    @GetMapping("/details")
    @RolesAllowed("role-user")
    UserDomainModel getUserInfo() {
        return null;
    }
}