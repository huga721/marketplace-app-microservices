package huberts.spring.user.adapter.in.web;

import huberts.spring.user.adapter.in.web.resource.EditRequest;
import huberts.spring.user.domain.model.UserDomainModel;
import huberts.spring.user.domain.port.in.UserServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "User REST API")
@Validated
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserServicePort userServicePort;

    @GetMapping
    List<UserDomainModel> getAllUsers() {
        LOGGER.info(">> UserController call: getting all users");
        return userServicePort.getAllUsers();
    }

    @GetMapping("/{userId}")
    UserDomainModel getUserById(@PathVariable Long userId) {
        LOGGER.info(">> UserController call: receiving user with id {}", userId);
        return userServicePort.getUserById(userId);
    }

    @PatchMapping()
    @RolesAllowed("role-user")
    UserDomainModel editUser(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody EditRequest editRequest) {
        LOGGER.info(">> UserController call: editing authenticated user {}", editRequest);
        String keycloakId = jwt.getSubject();
        return userServicePort.editUserByKeycloakId(keycloakId, editRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("role-user")
    void deleteUser(@AuthenticationPrincipal Jwt jwt) {
        LOGGER.info(">> UserController call: deleting user");
        String keycloakId = jwt.getSubject();
        userServicePort.deleteUserByKeycloakId(keycloakId);
    }
}