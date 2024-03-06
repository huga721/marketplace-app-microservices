package huberts.spring.product.adapter.out.persistance.entity;

import huberts.spring.product.application.enums.Quality;
import huberts.spring.product.application.enums.Status;
import huberts.spring.product.application.valueobject.KeycloakId;
import huberts.spring.product.application.valueobject.Description;
import huberts.spring.product.application.valueobject.Name;
import huberts.spring.product.application.valueobject.Price;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity extends BaseEntityAudit {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    })
    private Name name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "description", nullable = false))
    })
    private Description description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "price", nullable = false))
    })
    private Price price;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "keycloak_id", nullable = false))
    })
    private KeycloakId keycloakId;

    @Enumerated(EnumType.STRING)
    private Quality quality;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public static ProductEntity of(Name name, Description description, Price price, KeycloakId keycloakId, Quality quality, Status status) {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setName(name);
        productEntity.setDescription(description);
        productEntity.setPrice(price);
        productEntity.setKeycloakId(keycloakId);
        productEntity.setQuality(quality);
        productEntity.setStatus(status);

        return productEntity;
    }
}