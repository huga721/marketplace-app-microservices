package huberts.spring.notification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDomainModel {

    private Long id;
    private String message;
    private LocalDateTime timestamp;
}