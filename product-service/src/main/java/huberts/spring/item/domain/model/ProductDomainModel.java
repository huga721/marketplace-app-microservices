package huberts.spring.item.domain.model;

import huberts.spring.item.adapter.in.web.resource.ProductRequest;
import huberts.spring.item.common.model.Quality;
import huberts.spring.item.common.model.Status;
import huberts.spring.item.common.exception.ProductStatusException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDomainModel {

    private Long id;
    private String name;
    private String description;
    private Long price;
    private Quality quality;
    private Status status;
    private LocalDateTime createdTime;
    private String keycloakId;

    public boolean isActive() {
        return status.equals(Status.ACTIVE);
    }

    public ProductDomainModel update(ProductRequest productRequest) {
        if (status == Status.INACTIVE) {
            throw new ProductStatusException("Status of item is inactive");
        }
        return new ProductDomainModel(id, productRequest.name(), productRequest.description(),
                productRequest.price(), productRequest.quality(), status, createdTime, keycloakId);
    }

    public ProductDomainModel markInactive() {
        if (status == Status.INACTIVE) {
            throw new ProductStatusException("Status of item is inactive");
        }
        status = Status.INACTIVE;
        return this;
    }

    public ProductDomainModel returnActiveProduct() {
        if (isActive()) {
            return this;
        }
        return null;
    }
}
