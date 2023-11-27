package huberts.spring.product.adapter.in.web.integration;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@ActiveProfiles("test")
@SpringBootTest
public class ContainerIT {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer;
    @Container
    private static final KeycloakContainer keycloakContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres")
                .withReuse(true);
        postgreSQLContainer.start();
        keycloakContainer = new KeycloakContainer("quay.io/keycloak/keycloak:22.0.1")
                .withRealmImportFile("marketplace-realm.json")
                .withAdminUsername("admin")
                .withAdminPassword("password");
        keycloakContainer.start();
    }

    @DynamicPropertySource
    public static void containerConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloakContainer.getAuthServerUrl() + "/realms/marketplace-app-realm");
        registry.add("keycloak.server-url", keycloakContainer::getAuthServerUrl);
        registry.add("keycloak.username", keycloakContainer::getAdminUsername);
        registry.add("keycloak.password", keycloakContainer::getAdminPassword);
    }
}