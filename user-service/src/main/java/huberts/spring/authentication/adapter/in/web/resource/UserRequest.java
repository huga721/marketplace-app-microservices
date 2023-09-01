package huberts.spring.authentication.adapter.in.web.resource;

public record UserRequest(
        String username,
        String password,
        String firstName,
        String lastName,
        String email) {
}
