package huberts.spring.notification.application.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Notification Service API",
        description = "This lists all the Notification Service API Calls. The Calls are OAuth2 secured, "
                + "so please use your Bearer Token to test them out.",
        version = "v1.0"),
        security = @SecurityRequirement(name = "security_auth"))
@SecurityScheme(
        name = "security_auth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT")
public class OpenAPIConfiguration {
}