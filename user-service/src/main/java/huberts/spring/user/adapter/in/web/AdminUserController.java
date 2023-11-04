package huberts.spring.user.adapter.in.web;

import huberts.spring.user.adapter.in.web.resource.EditRequest;
import huberts.spring.user.domain.model.UserDomainModel;
import huberts.spring.user.domain.port.in.UserServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "admin", description = "Admin User REST API")
@RestController
@RequestMapping("/api/user/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserServicePort userServicePort;

    @PatchMapping("/{userId}")
    @RolesAllowed("role-admin")
    UserDomainModel editUserById(@PathVariable Long userId, @RequestBody EditRequest editRequest) {
        return userServicePort.editUserById(userId, editRequest);
    }

    @DeleteMapping("/{userId}")
    @RolesAllowed("role-admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserById(@PathVariable Long userId) {
        userServicePort.deleteUserById(userId);
    }
}