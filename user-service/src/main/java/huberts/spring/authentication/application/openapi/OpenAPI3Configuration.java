package huberts.spring.authentication.application.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "User Service APIs",
        description = "This lists all the Department Service API Calls. The Calls are OAuth2 secured, "
                + "so please use your client ID and Secret to test them out.",
        version = "v1.0"))
@SecurityScheme(name = "security_auth", type = SecuritySchemeType.HTTP, scheme = "bearer")
public class OpenAPI3Configuration {

    private static final String SCHEME_NAME = "bearerAuth";
    private static final String SCHEME = "bearer";

//    @Bean
//    OpenAPI customOpenApi() {
//        return new OpenAPI()
////                .info(infoProvider.provide())
//                .components(new Components()
//                        .addSecuritySchemes(SCHEME_NAME, createBearerScheme()))
//                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME));
//    }

//    private SecurityScheme createBearerScheme() {
//        return new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP)
//                .scheme(SCHEME);
//    }
}