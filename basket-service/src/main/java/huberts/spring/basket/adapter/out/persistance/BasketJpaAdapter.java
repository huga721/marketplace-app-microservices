package huberts.spring.basket.adapter.out.persistance;

import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import huberts.spring.basket.adapter.out.persistance.mapper.BasketJpaMapper;
import huberts.spring.basket.adapter.out.persistance.repository.BasketRepository;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.port.BasketJpaPort;
import lombok.RequiredArgsConstructor;

import static huberts.spring.basket.adapter.out.persistance.mapper.BasketJpaMapper.BASKET_JPA_MAPPER;

@RequiredArgsConstructor
public class BasketJpaAdapter implements BasketJpaPort {

    private final BasketRepository basketRepository;

    @Override
    public BasketDomainModel createBasket() {
        BasketEntity basketEntity = new BasketEntity();
        basketRepository.save(basketEntity);
        return BASKET_JPA_MAPPER.toDomainModel(basketEntity);
    }
}
