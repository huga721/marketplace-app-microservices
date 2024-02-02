package huberts.spring.order.adapter.out.feign.basket;

import huberts.spring.order.adapter.out.feign.basket.model.BasketDomainModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "basket-service",
        configuration = FeignClientConfiguration.class)
public interface BasketFeignClient {

    @GetMapping(
            value = "/api/basket/details",
            consumes = "application/json",
            produces = "application/json")
    BasketDomainModel getBasketDetails();
}
