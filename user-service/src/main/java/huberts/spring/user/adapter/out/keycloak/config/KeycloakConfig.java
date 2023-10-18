package huberts.spring.user.adapter.out.keycloak.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    private final String url = "http://localhost:8181";
    private final String realm = "marketplace-app-realm";
    private final String clientId = "marketplace-app";
    private final String clientSecret = "TehmCIP2NYurYk1J2d8XfszkNYc9ujZg";
    private final String username = "admin";
    private final String password = "password";

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public Keycloak keycloak() {
        return Keycloak.getInstance(url, realm, username, password, clientId, clientSecret);
    }
}