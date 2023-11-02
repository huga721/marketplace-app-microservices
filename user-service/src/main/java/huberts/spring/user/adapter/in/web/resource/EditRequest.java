package huberts.spring.user.adapter.in.web.resource;

import jakarta.validation.constraints.NotBlank;

public record EditRequest(
        @NotBlank(message = "Field \"Username\" can not be blank.") String username,
        @NotBlank(message = "Field \"First Name\" can not be blank.") String firstName,
        @NotBlank(message = "Field \"Last Name\" can not be blank.") String lastName,
        @NotBlank(message = "Field \"Email\" can not be blank.") String email) {
}