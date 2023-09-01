package huberts.spring.item.adapter.out.persistance.entity;

import huberts.spring.item.common.model.Color;
import huberts.spring.item.common.model.Quality;
import huberts.spring.item.common.model.Size;
import huberts.spring.item.common.model.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Long price;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Quality quality;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Status status;
}
