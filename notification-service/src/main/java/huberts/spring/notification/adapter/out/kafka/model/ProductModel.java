package huberts.spring.notification.adapter.out.kafka.model;

import java.time.LocalDateTime;

public record ProductModel(
        Long id,
        String name,
        String description,
        Long price,
        String quality,
        String status,
        LocalDateTime createdTime,
        String keycloakId) {
}
