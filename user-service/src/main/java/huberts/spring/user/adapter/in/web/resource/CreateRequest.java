package huberts.spring.user.adapter.in.web.resource;

public record CreateRequest(
        String username,
        String password,
        String firstName,
        String lastName,
        String email) {
}
