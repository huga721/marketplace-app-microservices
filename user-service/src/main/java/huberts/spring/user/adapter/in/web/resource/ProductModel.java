package huberts.spring.user.adapter.in.web.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

    private Long id;
    private String name;
    private String description;
    private Long price;
    private String quality;
    private Status status;
    private LocalDateTime createdTime;
    private String keycloakId;

}
