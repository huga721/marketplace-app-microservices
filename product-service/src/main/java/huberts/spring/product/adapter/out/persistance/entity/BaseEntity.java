package huberts.spring.product.adapter.out.persistance.entity;

import huberts.spring.product.application.valueobject.id.ProductId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private String uuid = UUID.randomUUID().toString();

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof BaseEntity && Objects.equals(uuid, ((BaseEntity) o).uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "BaseEntity{" + "id=" + id +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}