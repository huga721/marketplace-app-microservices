package huberts.spring.basket.adapter.in.web;

import huberts.spring.basket.domain.port.BasketJpaPort;
import huberts.spring.basket.domain.port.BasketServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "baskets", description = "Basket operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class BasketController {

    private final BasketServicePort basketServicePort;

    @PostMapping("/add-product/{productId}")
    String addProductToBasket(@PathVariable Long productId) {
        return "xd";
    }
}
