package huberts.spring.basket.adapter.out.persistance;

import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import huberts.spring.basket.adapter.out.persistance.entity.BasketProductEntity;
import huberts.spring.basket.adapter.out.persistance.repository.BasketProductRepository;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;
import huberts.spring.basket.domain.port.out.BasketProductJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static huberts.spring.basket.adapter.out.persistance.mapper.BasketJpaMapper.BASKET_JPA_MAPPER;
import static huberts.spring.basket.adapter.out.persistance.mapper.BasketProductJpaMapper.BASKET_PRODUCT_JPA_MAPPER;

@Component
@RequiredArgsConstructor
public class BasketProductJpaAdapter implements BasketProductJpaPort {

    private final BasketProductRepository basketProductRepository;

    @Override
    public BasketProductDomainModel saveBasketProduct(BasketProductDomainModel basketProduct,
                                                      BasketDomainModel basketDomainModel) {
        BasketEntity basket = BASKET_JPA_MAPPER.toJpaEntity(basketDomainModel);
        BasketProductEntity basketProductEntity = BASKET_PRODUCT_JPA_MAPPER.toJpaEntity(basketProduct, basket);
        BasketProductEntity basketProductSaved = basketProductRepository.save(basketProductEntity);
        return BASKET_PRODUCT_JPA_MAPPER.toDomainModel(basketProductSaved);
    }

    @Override
    public List<BasketProductDomainModel> getAllBasketProducts() {
        return basketProductRepository.findAll().stream()
                .map(BASKET_PRODUCT_JPA_MAPPER::toDomainModel)
                .toList();
    }

    @Override
    public void deleteBasketProduct(BasketProductDomainModel basketProductDomainModel, BasketDomainModel basketDomainModel) {
        BasketEntity basketEntity = BASKET_JPA_MAPPER.toJpaEntity(basketDomainModel);
        BasketProductEntity basketProductEntity = BASKET_PRODUCT_JPA_MAPPER.toJpaEntity(basketProductDomainModel, basketEntity);
        basketProductRepository.delete(basketProductEntity);
    }
}
