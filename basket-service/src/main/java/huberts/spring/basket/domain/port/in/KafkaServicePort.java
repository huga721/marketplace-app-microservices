package huberts.spring.basket.domain.port.in;

import java.util.List;

public interface KafkaServicePort {

    void sendProductSoldEvent(List<Long> productsId);
}
