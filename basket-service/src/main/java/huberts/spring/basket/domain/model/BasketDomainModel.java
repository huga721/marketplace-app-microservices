package huberts.spring.basket.domain.model;

import huberts.spring.basket.adapter.out.feign.product.model.ProductDomainModel;
import huberts.spring.basket.adapter.out.feign.product.model.Status;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class BasketDomainModel {

    private Long id;
    private List<BasketProductDomainModel> basketProducts = new ArrayList<>();
    private Integer productNumber;
    private Long basketValue;
    private String keycloakId;
    private Status status = Status.ACTIVE;

    public void addProduct(BasketProductDomainModel basketProductDomainModel) {
        basketProducts.add(basketProductDomainModel);
    }

    public void removeProduct(BasketProductDomainModel basketProductDomainModel) {
        basketProducts.remove(basketProductDomainModel);
    }

    public boolean isUserNotAuthorizedToAddProduct(ProductDomainModel product) {
        String productKeycloakId = product.getKeycloakId();
        return keycloakId.equals(productKeycloakId);
    }

    public boolean isProductInBasket(ProductDomainModel product) {
        Long productId = product.getId();
        return basketProducts.stream()
                .anyMatch(products -> products.getProductId().equals(productId));
    }

    public void setInactiveStatus() {
        status = Status.INACTIVE;
    }

    public Long calculateBasketValue() {
        return basketProducts.stream()
                .mapToLong(BasketProductDomainModel::getProductValue)
                .sum();
    }

    public Integer returnProductNumber() {
        return basketProducts.size();
    }
}