package huberts.spring.basket.adapter.out.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic productSoldTopic() {
        return TopicBuilder.name("product.sell")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic basketCreateNotificationTopic() {
        return TopicBuilder.name("basket.create.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic basketAddProductNotificationTopic() {
        return TopicBuilder.name("basket.add.product.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic basketInactiveNotificationTopic() {
        return TopicBuilder.name("basket.inactive.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic basketRemoveProductNotificationTopic() {
        return TopicBuilder.name("basket.remove.product.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }
}