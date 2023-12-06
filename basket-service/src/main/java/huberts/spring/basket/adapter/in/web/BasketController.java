package huberts.spring.basket.adapter.in.web;

import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;
import huberts.spring.basket.domain.port.in.BasketServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "basket", description = "Basket REST API")
@Validated
@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {

    private final Logger LOGGER = LoggerFactory.getLogger(BasketController.class);
    private final BasketServicePort basketServicePort;

    @GetMapping()
    List<BasketDomainModel> getBaskets() {
        LOGGER.info(">> BasketController: getting all baskets");
        return basketServicePort.getBaskets();
    }

    @GetMapping("/{basketId}")
    BasketDomainModel getBasketById(@PathVariable Long basketId) {
        LOGGER.info(">> BasketController: getting basket by id: {}", basketId);
        return basketServicePort.getBasketById(basketId);
    }

    @GetMapping("/product")
    List<BasketProductDomainModel> getBasketProducts() {
        LOGGER.info(">> BasketController: getting all basket products");
        return basketServicePort.getBasketProducts();
    }

    @RolesAllowed("role-user")
    @GetMapping("/details")
    BasketDomainModel getBasketDetails(@AuthenticationPrincipal Jwt jwt) {
        LOGGER.info(">> BasketController: getting basket details of current authenticated user");
        String keycloakId = jwt.getSubject();
        return basketServicePort.getBasketDetails(keycloakId);
    }

    @RolesAllowed("role-user")
    @PostMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    BasketDomainModel addProductToBasket(@PathVariable Long productId, @AuthenticationPrincipal Jwt jwt) {
        LOGGER.info(">> BasketController: adding product with id: {} to basket", productId);
        String keycloakId = jwt.getSubject();
        return basketServicePort.addProductToBasket(productId, keycloakId);
    }

    @RolesAllowed("role-user")
    @DeleteMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    String removeProductFromBasket(@PathVariable Long productId, @AuthenticationPrincipal Jwt jwt) {
        LOGGER.info(">> BasketController: removing product with id: {} from basket", productId);
        String keycloakId = jwt.getSubject();
        return basketServicePort.removeProductFromBasket(productId, keycloakId);
    }
}