package huberts.spring.user.adapter.in.web.resource;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Field \"Username\" can not be blank.") String username,
        @NotBlank(message = "Field \"Password\" can not be blank.") String password) {
}
