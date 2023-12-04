package huberts.spring.basket.adapter.out.persistance;

import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import huberts.spring.basket.adapter.out.persistance.repository.BasketRepository;
import huberts.spring.basket.adapter.out.feign.product.model.Status;
import huberts.spring.basket.application.exception.BasketNotFoundException;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.port.out.BasketJpaPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static huberts.spring.basket.adapter.out.persistance.mapper.BasketJpaMapper.BASKET_JPA_MAPPER;

@Component
@RequiredArgsConstructor
public class BasketJpaAdapter implements BasketJpaPort {

    private final Logger LOGGER = LoggerFactory.getLogger(BasketJpaAdapter.class);
    private final BasketRepository basketRepository;

    @Override
    public BasketDomainModel saveBasket(BasketDomainModel basketDomainModel) {
        BasketEntity basketEntity = BASKET_JPA_MAPPER.toJpaEntity(basketDomainModel);
        BasketEntity basketSaved = basketRepository.save(basketEntity);
        return BASKET_JPA_MAPPER.toDomainModel(basketSaved);
    }

    @Override
    public BasketDomainModel findBasketByKeycloakIdAndStatus(String keycloakId, Status status) {
        BasketEntity basketEntity = basketRepository.findBasketEntityByKeycloakIdAndStatus(keycloakId, status);
        return BASKET_JPA_MAPPER.toDomainModel(basketEntity);
    }

    @Override
    public void deleteBasket(BasketDomainModel basket) {
        BasketEntity basketEntity = BASKET_JPA_MAPPER.toJpaEntity(basket);
        basketRepository.delete(basketEntity);
    }

    @Override
    public BasketDomainModel getBasketById(Long basketId) {
        BasketEntity basket = basketRepository.findById(basketId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Basket with id = %s doesn't exist.", basketId);
                    LOGGER.warn("An exception occurred!", new BasketNotFoundException(errorMessage));
                    return new BasketNotFoundException(errorMessage);
                });
        return BASKET_JPA_MAPPER.toDomainModel(basket);
    }

    @Override
    public List<BasketDomainModel> getAllBaskets() {
        return basketRepository.findAll().stream()
                .map(BASKET_JPA_MAPPER::toDomainModel)
                .toList();
    }
}
