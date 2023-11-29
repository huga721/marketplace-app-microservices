package huberts.spring.basket.domain.port.out;

import huberts.spring.basket.adapter.out.feign.product.model.Status;
import huberts.spring.basket.domain.model.BasketDomainModel;

import java.util.List;

public interface BasketJpaPort {
    BasketDomainModel saveBasket(BasketDomainModel basketDomainModel);

    BasketDomainModel getBasketById(Long basketId);
    List<BasketDomainModel> getAllBaskets();

    BasketDomainModel findBasketByKeycloakIdAndStatus(String keycloakId, Status status);
}
