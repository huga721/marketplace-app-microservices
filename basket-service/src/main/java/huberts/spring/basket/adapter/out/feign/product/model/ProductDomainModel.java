package huberts.spring.basket.adapter.out.feign.product.model;

import lombok.Data;

import java.time.LocalDateTime;

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
}