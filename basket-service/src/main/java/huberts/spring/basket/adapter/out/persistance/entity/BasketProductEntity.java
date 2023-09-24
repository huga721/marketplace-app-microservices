package huberts.spring.basket.adapter.out.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "basket_product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long productValue;

    @ManyToOne()
    @JoinColumn(name = "basket_id")
    private BasketEntity basketEntity;
}
