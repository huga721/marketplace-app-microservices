package huberts.spring.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Autowired
    RouteDefinitionLocator locator;

    @Bean
    public List<GroupedOpenApi> apis() {
        List<RouteDefinition> definitions = locator.getRouteDefinitions()
                .collectList()
                .block();

        assert definitions != null;

        return definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .map(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("-service", "");
                    return GroupedOpenApi.builder()
                            .pathsToMatch("/" + name + "/**")
                            .group(name)
                            .build();
                })
                .collect(toList());
    }
}