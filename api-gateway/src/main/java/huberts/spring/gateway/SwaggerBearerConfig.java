//package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.swagger.authorization;
//
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@OpenAPIDefinition
//public class SwaggerBearerConfig {
//
//    private static final String SCHEME_NAME = "bearerAuth";
//    private static final String SCHEME = "bearer";
//
//    @Bean
//    OpenAPI customOpenApi() {
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes(SCHEME_NAME, createBearerScheme()))
//                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME));
//    }
//
//    private SecurityScheme createBearerScheme() {
//        return new SecurityScheme()
//
//}