package huberts.spring.basket.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketDomainModel {

    private Long id;
    private List<BasketProductDomainModel> basketProducts;

    public List<BasketProductDomainModel> addProductToBasket(BasketProductDomainModel basketProductDomainModel) {
        basketProducts.add(basketProductDomainModel);
        return basketProducts;
    }
}
