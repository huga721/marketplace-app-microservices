package huberts.spring.product.domain.model;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.application.enums.Quality;
import huberts.spring.product.application.enums.Status;
import huberts.spring.product.application.exception.ProductStatusException;
import huberts.spring.product.application.valueobject.KeycloakId;
import huberts.spring.product.application.valueobject.id.ProductId;
import huberts.spring.product.application.valueobject.Description;
import huberts.spring.product.application.valueobject.Name;
import huberts.spring.product.application.valueobject.Price;
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
    private Name name;
    private Description description;
    private Price price;
    private Quality quality;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private KeycloakId keycloakId;

    public boolean isActive() {
        return status.equals(Status.ACTIVE);
    }

    public ProductDomainModel update(ProductRequest productRequest) {
        validateStatus();

        name = Name.of(productRequest.name());
        description = Description.of(productRequest.description());
        price = Price.of(productRequest.price());
        quality = productRequest.quality();
        return this;
    }

    public ProductDomainModel markInactive() {
        validateStatus();

        status = Status.INACTIVE;
        return this;
    }

    public void validateStatus() {
        if (status == Status.INACTIVE) {
            throw new ProductStatusException("Status of item is inactive");
        }
    }

    @Builder
    public static ProductDomainModel of(Name name, Description description, Price price, Quality quality, Status status, KeycloakId keycloakId) {
        ProductDomainModel product = new ProductDomainModel();

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuality(quality);
        product.setStatus(status);
        product.setKeycloakId(keycloakId);

        return product;
    }
}