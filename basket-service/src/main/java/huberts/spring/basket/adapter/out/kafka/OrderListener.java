package huberts.spring.basket.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;
import huberts.spring.basket.domain.port.in.BasketServicePort;
import huberts.spring.basket.domain.port.in.KafkaNotificationServicePort;
import huberts.spring.basket.domain.port.in.KafkaProductServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderListener {

    private final BasketServicePort basketServicePort;
    private final KafkaProductServicePort kafkaProductServicePort;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order.complete", groupId = "basket")
    public void listens(final String in) throws JsonProcessingException {
        log.info(">> OrderListener: kafka listener: order.completed invoked: {}", in);
        BasketDomainModel basket = objectMapper.readValue(in, BasketDomainModel.class);

        List<Long> productsId = basket.getBasketProducts().stream()
                .map(BasketProductDomainModel::getProductId)
                .toList();
        log.info(">> OrderListener: products id to be send: {}", productsId);

        kafkaProductServicePort.sendProductSoldEvent(productsId);

        basketServicePort.setBasketInactive(basket);
    }
}