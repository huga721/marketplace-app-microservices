package huberts.spring.basket.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketProductDomainModel {

    private Long id;
    private Long productId;
    private Long productValue;
    private Long basketId;
}