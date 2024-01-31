package huberts.spring.order.domain.port.in;

import huberts.spring.order.adapter.out.feign.basket.model.BasketDomainModel;

public interface KafkaBasketServicePort {
    void sendBasketInactiveEvent(BasketDomainModel basketDomainModel);
}
