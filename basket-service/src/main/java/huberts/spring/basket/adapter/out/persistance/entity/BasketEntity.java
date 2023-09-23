package huberts.spring.basket.adapter.out.persistance.entity;

import huberts.spring.basket.adapter.out.feign.product.model.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "basket")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "basketEntity")
    private List<BasketProductEntity> basketProducts = new ArrayList<>();

    private Integer productNumber;
    private Long basketValue;
    private String keycloakId;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
}
