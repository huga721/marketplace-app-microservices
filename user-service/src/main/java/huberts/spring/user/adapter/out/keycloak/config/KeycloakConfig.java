package huberts.spring.user.adapter.out.keycloak.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

    @Value("${keycloak.server-url}")
    private String SERVER_URL;
    @Value("${keycloak.realm}")
    private String REALM;
    @Value("${keycloak.client-id}")
    private String CLIENT_ID;
    @Value("${keycloak.client-secret}")
    private String CLIENT_SECRET;
    @Value("${keycloak.username}")
    private String USERNAME;
    @Value("${keycloak.password}")
    private String PASSWORD;

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public Keycloak keycloak() {
        return Keycloak.getInstance(SERVER_URL, REALM, USERNAME, PASSWORD, CLIENT_ID, CLIENT_SECRET);
    }
}
