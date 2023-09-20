package huberts.spring.item.adapter.out.persistance.entity;

import huberts.spring.item.common.model.Quality;
import huberts.spring.item.common.model.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
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
    private LocalDateTime createdTime = LocalDateTime.now();
    private String keycloakId;

    @Enumerated(EnumType.STRING)
    private Quality quality;

    @Enumerated(EnumType.STRING)
    private Status status;
}
