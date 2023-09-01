package huberts.spring.basket.adapter.out.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "basket")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "basketId")
    private List<BasketProductEntity> basketProductEntities;
}
