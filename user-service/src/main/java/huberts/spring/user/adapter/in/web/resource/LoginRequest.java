package huberts.spring.user.adapter.in.web.resource;

public record LoginRequest(
        String username,
        String password) {
}
