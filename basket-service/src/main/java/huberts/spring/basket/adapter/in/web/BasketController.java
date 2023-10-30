package huberts.spring.basket.adapter.in.web;

import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;
import huberts.spring.basket.domain.port.in.BasketServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "baskets", description = "Basket operations")
@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketServicePort basketServicePort;

    @GetMapping()
    List<BasketDomainModel> getBaskets() {
        List<BasketDomainModel> baskets = basketServicePort.getBaskets();
        return baskets;
    }

    @GetMapping("/{basketId}")
    BasketDomainModel getBasketById(@PathVariable Long basketId) {
        BasketDomainModel basket = basketServicePort.getBasketById(basketId);
        return basket;
    }

    @GetMapping("/products")
    List<BasketProductDomainModel> getBasketProducts() {
        List<BasketProductDomainModel> basketProducts = basketServicePort.getBasketProducts();
        return basketProducts;
    }

    @RolesAllowed("role-user")
    @GetMapping("/details")
    BasketDomainModel getBasketDetails(@AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        BasketDomainModel basket = basketServicePort.getBasketDetails(keycloakId);
        return basket;
    }

    @RolesAllowed("role-user")
    @PostMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    BasketDomainModel addProductToBasket(@PathVariable Long productId,
                                         @AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        BasketDomainModel basket = basketServicePort.addProductToBasket(productId, keycloakId);
        return basket;
    }
}