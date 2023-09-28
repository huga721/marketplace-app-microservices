package huberts.spring.basket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BasketServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasketServiceApplication.class, args);
    }
}