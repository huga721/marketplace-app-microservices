package huberts.spring.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan({"huberts.spring.item.adapter.out.persistance", "huberts.spring.item.common", "huberts.spring.item.domain"})
@SpringBootApplication
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

//    @Bean
//    public ProductJpaPort productJpaPort() {
//        return productJpaPort();
//    }
}