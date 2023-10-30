package huberts.spring.basket.application;

import huberts.spring.basket.adapter.out.feign.product.ProductFeignClient;
import huberts.spring.basket.application.exception.ProductInBasketException;
import huberts.spring.basket.application.exception.UnauthorizedProductAdditionException;
import huberts.spring.basket.adapter.out.feign.product.model.ProductDomainModel;
import huberts.spring.basket.adapter.out.feign.product.model.Status;
import huberts.spring.basket.application.exception.BasketNotFoundException;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;
import huberts.spring.basket.domain.port.in.BasketServicePort;
import huberts.spring.basket.domain.port.out.BasketJpaPort;
import huberts.spring.basket.domain.port.out.BasketProductJpaPort;
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
    private final ProductFeignClient productFeignClient;

    @Override
    public BasketDomainModel addProductToBasket(Long productId, String keycloakId) {
        LOGGER.warn("Attempt to adding product to basket");

        BasketDomainModel basket = basketJpaPort.findBasketByKeycloakIdAndStatus(keycloakId, Status.ACTIVE);

        if (basket == null) {
            basket = new BasketDomainModel();
            basket.setKeycloakId(keycloakId);
            basket = basketJpaPort.saveBasket(basket);
        }

        ProductDomainModel product = productFeignClient.getProductById(productId);

        if (!basket.isUserAuthorizedToAddProduct(product)) {
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
        basketProduct = basketProductJpaPort.createBasketProduct(basketProduct, basket);

        basket.addProductToBasket(basketProduct);
        BasketDomainModel basketSaved = basketJpaPort.saveBasket(basket);
        LOGGER.info("Added product {} to a basket {}", basketProduct, basket);
        return basketSaved;
    }

    @Override
    public BasketDomainModel getBasketById(Long basketId) {
        return null;
    }

    @Override
    public List<BasketDomainModel> getBaskets() {
        return basketJpaPort.getAllBaskets();
    }

    @Override
    public List<BasketProductDomainModel> getBasketProducts() {
        return basketProductJpaPort.getAllBasketProducts();
    }

    @Override
    public BasketDomainModel getBasketDetails(String keycloakId) {
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
        basketDomainModel.setInactiveStatus();
        BasketDomainModel basketSaved = basketJpaPort.saveBasket(basketDomainModel);
        return basketSaved;
    }
}