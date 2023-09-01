package huberts.spring.authentication.adapter.in.web.resource;

public record LoginRequest(
        String username,
        String password) {
}
