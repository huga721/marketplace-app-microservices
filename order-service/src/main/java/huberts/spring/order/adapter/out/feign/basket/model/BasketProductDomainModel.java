package huberts.spring.order.adapter.out.feign.basket.model;

import lombok.Data;

@Data
public class BasketProductDomainModel {

    private Long id;
    private Long productId;
    private Long productValue;
    private Long basketId;
}