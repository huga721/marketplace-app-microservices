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
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions()
                .collectList()
                .block();
        assert definitions != null;
        definitions.stream().filter(routeDefinition -> routeDefinition
                .getId()
                .matches(".*-service"))
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId()
                            .replaceAll("-service", "");
                    log.info(name);
                    groups.add(GroupedOpenApi.builder()
                            .pathsToMatch("/" + name + "/**").group(name).build());
                    log.info(GroupedOpenApi.builder()
                            .pathsToMatch("/" + name + "/**").group(name).build().getPathsToMatch().toString());
                });
        return groups;
    }
}