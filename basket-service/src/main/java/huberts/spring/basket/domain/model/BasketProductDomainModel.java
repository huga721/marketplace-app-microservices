package huberts.spring.basket.domain.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BasketProductDomainModel {

    private Long id;
    private Long productId;


}
