package huberts.spring.order.application.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Order Service API",
        description = "This lists all the Order Service API Calls. The Calls are OAuth2 secured, "
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