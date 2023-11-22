package huberts.spring.user.adapter.in.web.resource;

import jakarta.validation.constraints.NotBlank;

public record CreateRequest(
        @NotBlank(message = "Field \"Username\" can not be blank.") String username,
        @NotBlank(message = "Field \"Password\" can not be blank.") String password,
        @NotBlank(message = "Field \"First name\" can not be blank.") String firstName,
        @NotBlank(message = "Field \"Last name\" can not be blank.") String lastName,
        @NotBlank(message = "Field \"email\" can not be blank.") String email) {
}
