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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "admin", description = "Admin User REST API")
@Validated
@RestController
@RequestMapping("/api/user/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);
    private final UserServicePort userServicePort;

    @PatchMapping("/{userId}")
    @RolesAllowed("role-admin")
    UserDomainModel editUserById(@PathVariable Long userId, @Valid @RequestBody EditRequest editRequest) {
        LOGGER.info("AdminUserController call: editing user with id {}, request body: {}", userId, editRequest);
        return userServicePort.editUserById(userId, editRequest);
    }

    @DeleteMapping("/{userId}")
    @RolesAllowed("role-admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserById(@PathVariable Long userId) {
        LOGGER.info("AdminUserController call: deleting user with id {}", userId);
        userServicePort.deleteUserById(userId);
    }
}