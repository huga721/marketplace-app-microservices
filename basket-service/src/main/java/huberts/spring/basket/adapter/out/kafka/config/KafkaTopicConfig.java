package huberts.spring.basket.adapter.out.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic productSoldTopic() {
        return TopicBuilder.name("product.sold")
                .partitions(10)
                .replicas(1)
                .build();
    }
}