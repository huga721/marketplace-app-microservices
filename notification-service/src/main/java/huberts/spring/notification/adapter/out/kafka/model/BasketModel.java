package huberts.spring.notification.adapter.out.kafka.model;

import java.util.List;

public record BasketModel(
        Long id,
        List<BasketProductModel> basketProducts,
        Integer productNumber,
        Long basketValue,
        String keycloakId,
        String status) {
}
