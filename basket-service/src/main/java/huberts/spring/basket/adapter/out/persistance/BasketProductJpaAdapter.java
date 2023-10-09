package huberts.spring.basket.adapter.out.persistance;

import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import huberts.spring.basket.adapter.out.persistance.entity.BasketProductEntity;
import huberts.spring.basket.adapter.out.persistance.mapper.BasketProductJpaMapper;
import huberts.spring.basket.adapter.out.persistance.repository.BasketProductRepository;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;
import huberts.spring.basket.domain.port.out.BasketProductJpaPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static huberts.spring.basket.adapter.out.persistance.mapper.BasketJpaMapper.BASKET_JPA_MAPPER;
import static huberts.spring.basket.adapter.out.persistance.mapper.BasketProductJpaMapper.BASKET_PRODUCT_JPA_MAPPER;

@Component
@Transactional
@RequiredArgsConstructor
public class BasketProductJpaAdapter implements BasketProductJpaPort {

    private final Logger LOGGER = LoggerFactory.getLogger(BasketProductJpaMapper.class);

    private final BasketProductRepository basketProductRepository;

    @Override
    @Transactional
    public BasketProductDomainModel createBasketProduct(BasketProductDomainModel basketProduct,
                                                        BasketDomainModel basketDomainModel) {
        BasketEntity basket = BASKET_JPA_MAPPER.toJpaEntity(basketDomainModel);
        BasketProductEntity basketProductEntity = BASKET_PRODUCT_JPA_MAPPER.toJpaEntity(basketProduct, basket);
        BasketProductEntity basketProductSaved = basketProductRepository.save(basketProductEntity);
        return BASKET_PRODUCT_JPA_MAPPER.toDomainModel(basketProductSaved);
    }

    @Override
    @Transactional
    public List<BasketProductDomainModel> getAllBasketProducts() {
        List<BasketProductDomainModel> basketProducts = basketProductRepository.findAll().stream()
                .map(BASKET_PRODUCT_JPA_MAPPER::toDomainModel)
                .toList();
        return basketProducts;
    }
}
