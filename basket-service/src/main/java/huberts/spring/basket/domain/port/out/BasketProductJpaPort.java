package huberts.spring.basket.domain.port.out;

import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;

import java.util.List;

public interface BasketProductJpaPort {
    BasketProductDomainModel saveBasketProduct(BasketProductDomainModel basketProductDomainModel,
                                               BasketDomainModel basketDomainModel);

    List<BasketProductDomainModel> getAllBasketProducts();

    void deleteBasketProduct(BasketProductDomainModel basketProductDomainModel, BasketDomainModel basketDomainModel);
}
