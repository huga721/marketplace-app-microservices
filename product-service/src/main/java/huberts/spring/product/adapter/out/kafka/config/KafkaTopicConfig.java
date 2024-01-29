package huberts.spring.product.adapter.out.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic productCreatedNotificationTopic() {
        return TopicBuilder.name("topic.created.notification")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic productInactiveNotificationTopic() {
        return TopicBuilder.name("topic.inactive.notification")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic productEditNotificationTopic() {
        return TopicBuilder.name("topic.edit.notification")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic productDeleteNotificationTopic() {
        return TopicBuilder.name("topic.delete.notification")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic productDeleteByAdminNotificationTopic() {
        return TopicBuilder.name("topic.delete.admin.notification")
                .partitions(10)
                .replicas(1)
                .build();
    }
}