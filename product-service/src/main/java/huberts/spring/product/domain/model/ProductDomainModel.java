package huberts.spring.product.domain.model;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.application.exception.ProductStatusException;
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
    private String quality;
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
                productRequest.price(), productRequest.quality().toString(), status, createdTime, keycloakId);
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