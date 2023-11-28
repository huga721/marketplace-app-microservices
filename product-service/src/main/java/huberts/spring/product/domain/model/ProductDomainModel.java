package huberts.spring.product.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.application.exception.ProductStatusException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
        name = productRequest.name();
        description = productRequest.description();
        price = productRequest.price();
        quality = productRequest.quality().toString();
        return this;
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