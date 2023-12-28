package huberts.spring.order.adapter.out.feign.basket;

import feign.Response;
import feign.codec.ErrorDecoder;
import huberts.spring.order.adapter.out.feign.basket.model.exception.BasketNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasketClientErrorDecoder implements ErrorDecoder {

    private final Logger LOGGER = LoggerFactory.getLogger(BasketClientErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 404:
                String errorMessage = "Basket not found";
                LOGGER.warn("An exception occurred!", new BasketNotFoundException(errorMessage));
                throw new BasketNotFoundException(errorMessage);
            default:
                errorMessage = "Exception while getting basket details";
                LOGGER.warn("An exception occurred!", new RuntimeException(errorMessage));
                return new RuntimeException("Exception while getting basket details");
        }
    }
}