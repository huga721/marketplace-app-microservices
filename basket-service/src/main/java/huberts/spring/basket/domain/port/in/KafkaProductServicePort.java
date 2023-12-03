package huberts.spring.basket.domain.port.in;

import java.util.List;

public interface KafkaProductServicePort {

    void sendProductSoldEvent(List<Long> productsId);
}
