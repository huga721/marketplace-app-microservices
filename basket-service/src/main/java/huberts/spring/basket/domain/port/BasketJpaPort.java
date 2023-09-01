package huberts.spring.basket.domain.port;

import huberts.spring.basket.domain.model.BasketDomainModel;

public interface BasketJpaPort {
    BasketDomainModel createBasket();
}
