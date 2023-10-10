package huberts.spring.basket.adapter.out.persistance;

import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import huberts.spring.basket.adapter.out.persistance.repository.BasketRepository;
import huberts.spring.basket.adapter.out.feign.product.model.Status;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.port.out.BasketJpaPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static huberts.spring.basket.adapter.out.persistance.mapper.BasketJpaMapper.BASKET_JPA_MAPPER;

@Component
@RequiredArgsConstructor
public class BasketJpaAdapter implements BasketJpaPort {

    private final BasketRepository basketRepository;

    @Override
    @Transactional
    public BasketDomainModel saveBasket(BasketDomainModel basketDomainModel) {
        BasketEntity basketEntity = BASKET_JPA_MAPPER.toJpaEntity(basketDomainModel);
        BasketEntity basketSaved = basketRepository.save(basketEntity);
        return BASKET_JPA_MAPPER.toDomainModel(basketSaved);
    }

    @Override
    @Transactional
    public BasketDomainModel findBasketByKeycloakIdAndStatus(String keycloakId, Status status) {
        BasketEntity basketEntity = basketRepository.findBasketEntityByKeycloakIdAndStatus(
                keycloakId, status);
        return BASKET_JPA_MAPPER.toDomainModel(basketEntity);
    }

    @Override
    public BasketDomainModel findBasketById(Long basketId) {
        return null;
    }

    @Override
    @Transactional
    public List<BasketDomainModel> getAllBaskets() {
        return basketRepository.findAll().stream()
                .map(BASKET_JPA_MAPPER::toDomainModel)
                .toList();
    }
}
