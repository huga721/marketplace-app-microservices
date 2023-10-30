package huberts.spring.basket.domain.port.in;

import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;

import java.util.List;

public interface BasketServicePort {
    BasketDomainModel addProductToBasket(Long productId, String keycloakId);

    BasketDomainModel getBasketById(Long basketId);

    List<BasketDomainModel> getBaskets();

    List<BasketProductDomainModel> getBasketProducts();

    BasketDomainModel getBasketDetails(String keycloakId);

    BasketDomainModel setBasketInactive(BasketDomainModel basketDomainModel);
}
