package huberts.spring.order.adapter.in.web.resource;

import jakarta.validation.constraints.NotBlank;

public record DeliveryAddress(
        @NotBlank(message = "Field \"city\" can not be blank.") String city,
        @NotBlank(message = "Field \"address\" can not be blank.") String address,
        @NotBlank(message = "Field \"postal code\" can not be blank.") String postalCode) {
}