package huberts.spring.basket.adapter.out.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "basket_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long productId;

    @ManyToOne()
    @JoinColumn(name = "basket_id", nullable = false)
    private BasketEntity basketId;
}
