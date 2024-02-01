package huberts.spring.basket.domain.port.in;

import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;

public interface KafkaNotificationServicePort {
    void sendBasketCreateNotificationEvent(BasketDomainModel basketDomainModel);

    void sendBasketAddProductNotificationEvent(BasketDomainModel basketDomainModel);

    void sendBasketRemoveProductNotificationEvent(BasketDomainModel basketDomainModel);

    void sendBasketInactiveNotificationEvent(BasketDomainModel basketDomainModel);
}