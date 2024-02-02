package huberts.spring.order.adapter.out.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderCompletedTopic() {
        return TopicBuilder.name("order.complete")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderNotificationTopic() {
        return TopicBuilder.name("order.create.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }
}