package huberts.spring.order.adapter.out.feign.basket.model;

import lombok.Data;

import java.util.List;

@Data
public class BasketDomainModel {

    private Long id;
    private List<BasketProductDomainModel> basketProducts;
    private Integer productNumber;
    private Long basketValue;
    private String keycloakId;
    private Status status;
}