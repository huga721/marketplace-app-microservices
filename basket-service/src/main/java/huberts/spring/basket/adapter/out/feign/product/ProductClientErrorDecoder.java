package huberts.spring.basket.adapter.out.feign.product;

import feign.Response;
import feign.codec.ErrorDecoder;
import huberts.spring.basket.adapter.out.feign.product.model.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductClientErrorDecoder implements ErrorDecoder {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductClientErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 404:
                String errorMessage = "Product not found";
                LOGGER.warn("An exception occurred!", new ProductNotFoundException(errorMessage));
                throw new ProductNotFoundException(errorMessage);
            default:
                errorMessage = "Exception while getting product details";
                LOGGER.warn("An exception occurred!", new RuntimeException(errorMessage));
                return new RuntimeException("Exception while getting product details");
        }
    }
}