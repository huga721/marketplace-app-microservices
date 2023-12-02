package huberts.spring.product.adapter.in.web.resource;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotBlank(message = "Field \"name\" can not be blank.") String name,
        @NotBlank(message = "Field \"description\" can not be blank.") String description,
        @Min(value = 1, message = "Price should be not less than 1.") Long price,
        @NotNull(message = "Field \"quality\" can not be blank.") Quality quality) {
}