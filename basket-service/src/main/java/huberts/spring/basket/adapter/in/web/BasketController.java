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
@RequiredArgsConstructor
public class BasketController {

    private final BasketServicePort basketServicePort;

    @RolesAllowed("role-user")
    @PostMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    BasketDomainModel addProductToBasket(@PathVariable Long productId,
                                         @AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        return basketServicePort.addProductToBasket(productId, keycloakId);
    }

    @RolesAllowed("role-user")
    @GetMapping("/details")
    BasketDomainModel getBasketDetails(@AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        return basketServicePort.getBasketDetails(keycloakId);
    }

    @RolesAllowed("role-user")
    @GetMapping()
    List<BasketDomainModel> getBaskets() {
        return basketServicePort.getBaskets();
    }

    @RolesAllowed("role-user")
    @GetMapping("/products")
    List<BasketProductDomainModel> getBasketProducts() {
        return basketServicePort.getBasketProducts();
    }

    @RolesAllowed("role-user")
    @GetMapping("/{basketId}")
    BasketDomainModel getBasketById(@PathVariable Long basketId) {
        return basketServicePort.getBasketById(basketId);
    }
}
