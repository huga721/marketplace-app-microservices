package huberts.spring.product.adapter.out.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic productCreatedNotificationTopic() {
        return TopicBuilder.name("product.created.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic productInactiveNotificationTopic() {
        return TopicBuilder.name("product.inactive.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic productEditNotificationTopic() {
        return TopicBuilder.name("product.edit.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic productDeleteNotificationTopic() {
        return TopicBuilder.name("product.delete.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic productDeleteByAdminNotificationTopic() {
        return TopicBuilder.name("product.delete.admin.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }
}