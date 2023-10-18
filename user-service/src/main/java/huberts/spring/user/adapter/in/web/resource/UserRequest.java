package huberts.spring.user.adapter.in.web.resource;

public record UserRequest(
        String username,
        String password,
        String firstName,
        String lastName,
        String email) {
}
