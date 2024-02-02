package huberts.spring.basket.application;

import huberts.spring.basket.adapter.out.feign.product.ProductFeignClient;
import huberts.spring.basket.adapter.out.feign.product.model.exception.ProductNotFoundException;
import huberts.spring.basket.application.exception.ProductInBasketException;
import huberts.spring.basket.application.exception.StatusException;
import huberts.spring.basket.application.exception.UnauthorizedProductAdditionException;
import huberts.spring.basket.adapter.out.feign.product.model.ProductDomainModel;
import huberts.spring.basket.adapter.out.feign.product.model.Status;
import huberts.spring.basket.application.exception.BasketNotFoundException;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;
import huberts.spring.basket.domain.port.in.BasketServicePort;
import huberts.spring.basket.domain.port.in.KafkaNotificationServicePort;
import huberts.spring.basket.domain.port.out.BasketJpaPort;
import huberts.spring.basket.domain.port.out.BasketProductJpaPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService implements BasketServicePort {

    private final Logger LOGGER = LoggerFactory.getLogger(BasketService.class);
    private final BasketJpaPort basketJpaPort;
    private final BasketProductJpaPort basketProductJpaPort;
    private final KafkaNotificationServicePort kafkaNotificationServicePort;
    private final ProductFeignClient productFeignClient;

    @Transactional
    @Override
    public BasketDomainModel addProductToBasket(Long productId, String keycloakId) {
        LOGGER.warn(">> BasketService: attempt on adding product to basket");

        BasketDomainModel basket = basketJpaPort.findBasketByKeycloakIdAndStatus(keycloakId, Status.ACTIVE);
        if (basket == null) {
            basket = new BasketDomainModel();
            basket.setKeycloakId(keycloakId);
            basket = basketJpaPort.saveBasket(basket);
            kafkaNotificationServicePort.sendBasketCreateNotificationEvent(basket);
            LOGGER.info(">> BasketService: created new basket: {}", basket);
        }

        ProductDomainModel product = productFeignClient.getProductById(productId);
        if (product.getStatus().equals(Status.INACTIVE)) {
            String errorMessage = "Requested product is Inactive.";
            LOGGER.warn("An exception occurred!", new StatusException(errorMessage));
            throw new StatusException(errorMessage);
        }

        if (basket.isUserNotAuthorizedToAddProduct(product)) {
            String errorMessage = "Can't add own product to a basket";
            LOGGER.warn("An exception occurred!", new UnauthorizedProductAdditionException(errorMessage));
            throw new UnauthorizedProductAdditionException(errorMessage);
        }

        if (basket.isProductInBasket(product)) {
            String errorMessage = "Product is already in basket";
            LOGGER.warn("An exception occurred!", new ProductInBasketException(errorMessage));
            throw new ProductInBasketException(errorMessage);
        }

        BasketProductDomainModel basketProduct = new BasketProductDomainModel();
        basketProduct.setBasketId(basket.getId());
        basketProduct.setProductValue(product.getPrice());
        basketProduct.setProductId(productId);

        basketProduct = basketProductJpaPort.saveBasketProduct(basketProduct, basket);

        basket.addProduct(basketProduct);
        BasketDomainModel basketSaved = basketJpaPort.saveBasket(basket);
        kafkaNotificationServicePort.sendBasketAddProductNotificationEvent(basketSaved);
        LOGGER.info(">> BasketService: added product {} to a basket {}", basketProduct, basket);
        return basketSaved;
    }

    @Override
    public BasketDomainModel getBasketById(Long basketId) {
        LOGGER.info(">> BasketService: getting basket with id: {}", basketId);
        return basketJpaPort.getBasketById(basketId);
    }

    @Override
    public List<BasketDomainModel> getBaskets() {
        LOGGER.info(">> BasketService: getting all baskets");
        return basketJpaPort.getAllBaskets();
    }

    @Override
    public List<BasketProductDomainModel> getBasketProducts() {
        LOGGER.info(">> BasketService: getting all products");
        return basketProductJpaPort.getAllBasketProducts();
    }

    @Override
    public BasketDomainModel getBasketDetails(String keycloakId) {
        LOGGER.info(">> BasketService: getting basket details of user with keycloak id: {}", keycloakId);

        BasketDomainModel basket = basketJpaPort.findBasketByKeycloakIdAndStatus(keycloakId, Status.ACTIVE);
        if (basket == null) {
            String errorMessage = "Basket of current authenticated user is empty";
            LOGGER.warn("An exception occurred!", new BasketNotFoundException(errorMessage));
            throw new BasketNotFoundException(errorMessage);
        }
        return basket;
    }

    @Override
    public BasketDomainModel setBasketInactive(BasketDomainModel basketDomainModel) {
        LOGGER.info(">> BasketService: setting basket status as inactive");
        basketDomainModel.setInactiveStatus();
        kafkaNotificationServicePort.sendBasketInactiveNotificationEvent(basketDomainModel);
        return basketJpaPort.saveBasket(basketDomainModel);
    }

    @Transactional
    @Override
    public String removeProductFromBasket(Long productId, String keycloakId) {
        LOGGER.info(">> BasketService: removing product with id: {} from basket", productId);

        BasketDomainModel basket = basketJpaPort.findBasketByKeycloakIdAndStatus(keycloakId, Status.ACTIVE);
        LOGGER.info(">> BasketService: basket found {}", basket);

        if (basket == null) {
            String errorMessage = "Basket of current authenticated user is empty";
            LOGGER.warn("An exception occurred!", new BasketNotFoundException(errorMessage));
            throw new BasketNotFoundException(errorMessage);
        }

        kafkaNotificationServicePort.sendBasketRemoveProductNotificationEvent(basket);

        BasketProductDomainModel product = basket.getBasketProducts()
                .stream()
                .filter(basketProduct -> basketProduct.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> {
                    String errorMessage = "Not found product that is in user basket.";
                    LOGGER.warn("An exception occurred!", new ProductNotFoundException(errorMessage));
                    return new ProductNotFoundException(errorMessage);
                });

        basket.removeProduct(product);

        basketJpaPort.saveBasket(basket);
        basketProductJpaPort.deleteBasketProduct(product, basket);

        if (basket.getBasketProducts().size() == 0) {
            LOGGER.info(">> BasketService: basket does not contain any product, deleting basket {}", basket);
            basketJpaPort.deleteBasket(basket);
            return "Removed product from basket, deleted basket.";
        }

        LOGGER.info(">> BasketService: removed product: {} from basket", product);
        basketJpaPort.saveBasket(basket);
        return "Removed product from basket, basket sill available to use.";
    }
}