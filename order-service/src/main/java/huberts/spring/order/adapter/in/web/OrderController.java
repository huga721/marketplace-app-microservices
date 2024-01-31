package huberts.spring.order.adapter.in.web;

import huberts.spring.order.adapter.in.web.resource.OrderRequest;
import huberts.spring.order.domain.model.OrderDomainModel;
import huberts.spring.order.domain.port.in.OrderServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "order", description = "Order REST API")
@Validated
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final OrderServicePort orderServicePort;

    @GetMapping
    List<OrderDomainModel> getOrders() {
        LOGGER.info(">> OrderController: getting all orders");
        return orderServicePort.getOrders();
    }

    @GetMapping("/{orderId}")
    OrderDomainModel getOrderById(@PathVariable Long orderId) {
        LOGGER.info(">> OrderController: getting order with id: {}", orderId);
        return orderServicePort.getOrderById(orderId);
    }

    @RolesAllowed("role-user")
    @GetMapping("/user")
    List<OrderDomainModel> getAuthenticatedUserOrder(@AuthenticationPrincipal Jwt jwt) {
        LOGGER.info(">> OrderController: getting authenticated user order");
        String keycloakId = jwt.getSubject();
        return orderServicePort.getAuthenticatedUserOrder(keycloakId);
    }

    @RolesAllowed("role-user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OrderDomainModel createOrder(@Valid @RequestBody OrderRequest orderRequest, @AuthenticationPrincipal Jwt jwt) {
        LOGGER.info(">> OrderController: ordering products that are in basket");
        String keycloakId = jwt.getSubject();
        return orderServicePort.createOrder(orderRequest, keycloakId);
    }
}