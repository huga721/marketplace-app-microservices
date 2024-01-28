package huberts.spring.basket.adapter.in.web;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import huberts.spring.basket.adapter.ContainerIT;
import huberts.spring.basket.adapter.out.feign.product.ProductFeignClient;
import huberts.spring.basket.adapter.out.feign.product.model.ProductDomainModel;
import huberts.spring.basket.adapter.out.feign.product.model.Quality;
import huberts.spring.basket.adapter.out.feign.product.model.Status;
import huberts.spring.basket.adapter.out.feign.product.model.exception.ProductNotFoundException;
import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import huberts.spring.basket.adapter.out.persistance.entity.BasketProductEntity;
import huberts.spring.basket.adapter.out.persistance.repository.BasketProductRepository;
import huberts.spring.basket.adapter.out.persistance.repository.BasketRepository;
import huberts.spring.basket.application.exception.BasketNotFoundException;
import huberts.spring.basket.application.exception.ProductInBasketException;
import huberts.spring.basket.application.exception.StatusException;
import huberts.spring.basket.application.exception.UnauthorizedProductAdditionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BasketControllerIntegrationTest extends ContainerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketProductRepository basketProductRepository;

    @MockBean
    private ProductFeignClient productFeignClient;

    private BasketEntity basket;
    private BasketEntity basketToRemoveProductAndExist;
    private BasketEntity basketToRemoveProductAndDelete;
    private BasketProductEntity product;
    private BasketProductEntity productToRemoveAndDeleteBasket;
    private BasketProductEntity productToRemoveAndBasketExist;
    private BasketProductEntity productToRemoveAndBasketExistLast;


    @BeforeAll
    public void init() {
        basket = new BasketEntity();
        basketToRemoveProductAndExist = new BasketEntity();
        basketToRemoveProductAndDelete = new BasketEntity();
        product = new BasketProductEntity();
        productToRemoveAndDeleteBasket = new BasketProductEntity();
        productToRemoveAndBasketExist = new BasketProductEntity();
        productToRemoveAndBasketExistLast = new BasketProductEntity();

        product.setId(1L);
        product.setProductId(1L);
        product.setProductValue(50L);
        basket.setId(1L);
        basket.setKeycloakId("e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88");
        basket.setBasketValue(product.getProductValue());
        product.setBasketEntity(basket);
        basket.setProductNumber(1);

        productToRemoveAndDeleteBasket.setId(2L);
        productToRemoveAndDeleteBasket.setProductId(2L);
        productToRemoveAndDeleteBasket.setProductValue(100L);

        basketToRemoveProductAndDelete.setId(2L);
        basketToRemoveProductAndDelete.setKeycloakId("caedd3a9-062b-457b-94a1-b893f0b21b4e");
        basketToRemoveProductAndDelete.setBasketValue(productToRemoveAndDeleteBasket.getProductValue());
        productToRemoveAndDeleteBasket.setBasketEntity(basketToRemoveProductAndDelete);
        basketToRemoveProductAndDelete.setProductNumber(1);

        productToRemoveAndBasketExist.setId(3L);
        productToRemoveAndBasketExist.setProductId(3L);
        productToRemoveAndBasketExist.setProductValue(150L);

        productToRemoveAndBasketExistLast.setId(4L);
        productToRemoveAndBasketExistLast.setProductId(4L);
        productToRemoveAndBasketExistLast.setProductValue(200L);


        basketToRemoveProductAndExist.setId(3L);
        basketToRemoveProductAndExist.setKeycloakId("cbe10031-5ab7-4ff6-b740-e9b001d93dd1");
        basketToRemoveProductAndExist.setBasketValue(productToRemoveAndBasketExist.getProductValue() +
                productToRemoveAndBasketExistLast.getProductValue());
        productToRemoveAndBasketExist.setBasketEntity(basketToRemoveProductAndExist);
        productToRemoveAndBasketExistLast.setBasketEntity(basketToRemoveProductAndExist);
        basketToRemoveProductAndExist.setProductNumber(2);


        basketRepository.save(basket);
        basketRepository.save(basketToRemoveProductAndDelete);
        basketRepository.save(basketToRemoveProductAndExist);

        basketProductRepository.save(product);
        basketProductRepository.save(productToRemoveAndDeleteBasket);
        basketProductRepository.save(productToRemoveAndBasketExist);
        basketProductRepository.save(productToRemoveAndBasketExistLast);
        basketRepository.save(basket);
        basketRepository.save(basketToRemoveProductAndDelete);
        basketRepository.save(basketToRemoveProductAndExist);
    }

    @Test
    void shouldReturnAllBaskets_WithNoRole() throws Exception {
        final String link = "/api/basket";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void shouldReturnBasketById() throws Exception {
        final String link = "/api/basket/1";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productNumber").value(1))
                .andExpect(jsonPath("$.basketValue").value(50))
                .andExpect(jsonPath("$.keycloakId").value("e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88"))
                .andExpect(jsonPath("$.status").value(Status.ACTIVE.name()));
    }

    @Test
    void shouldNoReturnBasket_WhenBasketNotFound() throws Exception {
        final String link = "/api/basket/999";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BasketNotFoundException));
    }

    @Test
    void shouldGetAllBasketProducts() throws Exception {
        final String link = "/api/basket/product";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldGetBasketDetailsOfAuthenticatedUser() throws Exception {
        final String link = "/api/basket/details";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.basketProducts", hasSize(2)))
                .andExpect(jsonPath("$.basketProducts[0].id").value(1))
                .andExpect(jsonPath("$.basketProducts[0].productId").value(product.getProductId()))
                .andExpect(jsonPath("$.basketProducts[0].productValue").value(product.getProductValue()))
                .andExpect(jsonPath("$.basketProducts[0].basketId").value(product.getBasketEntity().getId()))
                .andExpect(jsonPath("$.productNumber").value(2))
                .andExpect(jsonPath("$.basketValue").value(200))
                .andExpect(jsonPath("$.keycloakId").value(basket.getKeycloakId()))
                .andExpect(jsonPath("$.status").value(basket.getStatus().name()));
    }

    @Test
    void shouldNotGetBasketDetailsOfAuthenticatedUser_WhenNoRole() throws Exception {
        final String link = "/api/basket/details";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldAddProductToExistingBasket() throws Exception {
        ProductDomainModel product = new ProductDomainModel();
        product.setId(4L);
        product.setStatus(Status.ACTIVE);
        product.setKeycloakId("caedd3a9-062b-457b-94a1-b893f0b21b4e");
        product.setName("Test Product");
        product.setDescription("Test description");
        product.setPrice(150L);
        product.setQuality(Quality.GOOD);
        product.setCreatedTime(LocalDateTime.now());

        Mockito.when(productFeignClient.getProductById(4L))
                .thenReturn(product);

        final String link = "/api/basket/product/4";

        mockMvc.perform(MockMvcRequestBuilders.post(link))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.basketProducts", hasSize(2)))
                .andExpect(jsonPath("$.basketProducts[1].id").value(5))
                .andExpect(jsonPath("$.basketProducts[1].productId").value(4))
                .andExpect(jsonPath("$.basketProducts[1].productValue").value(product.getPrice()))
                .andExpect(jsonPath("$.basketProducts[1].basketId").value(this.product.getBasketEntity().getId()))
                .andExpect(jsonPath("$.productNumber").value(2))
                .andExpect(jsonPath("$.basketValue").value(basket.getBasketValue() + product.getPrice()))
                .andExpect(jsonPath("$.keycloakId").value(basket.getKeycloakId()))
                .andExpect(jsonPath("$.status").value(basket.getStatus().name()));
    }

    @Test
    void shouldNotAddProductToBasket_WithNoRole() throws Exception {
        final String link = "/api/basket/product/3";

        mockMvc.perform(MockMvcRequestBuilders.post(link))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("keycloak/userToDelete.json")
    void shouldCreateBasketAndAddProductToBasket() throws Exception {
        ProductDomainModel product = new ProductDomainModel();
        product.setId(5L);
        product.setStatus(Status.ACTIVE);
        product.setKeycloakId("caedd3a9-062b-457b-94a1-b893f0b21b4e");
        product.setName("Test Product");
        product.setDescription("Test description");
        product.setPrice(150L);
        product.setQuality(Quality.GOOD);
        product.setCreatedTime(LocalDateTime.now());

        Mockito.when(productFeignClient.getProductById(5L))
                .thenReturn(product);

        final String link = "/api/basket/product/5";

        mockMvc.perform(MockMvcRequestBuilders.post(link))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.basketProducts", hasSize(1)))
                .andExpect(jsonPath("$.basketProducts[0].id").value(6))
                .andExpect(jsonPath("$.basketProducts[0].productId").value(product.getId()))
                .andExpect(jsonPath("$.basketProducts[0].productValue").value(product.getPrice()))
                .andExpect(jsonPath("$.basketProducts[0].basketId").value(4))
                .andExpect(jsonPath("$.productNumber").value(1))
                .andExpect(jsonPath("$.basketValue").value(product.getPrice()))
                .andExpect(jsonPath("$.keycloakId").value("ae885313-4d31-4682-8360-09456c7ac312"))
                .andExpect(jsonPath("$.status").value(Status.ACTIVE.name()));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotAddProductToBasket_WhenNoProductFound() throws Exception {
        Mockito.when(productFeignClient.getProductById(6L))
                .thenThrow(new ProductNotFoundException(""));

        final String link = "/api/basket/product/6";

        mockMvc.perform(MockMvcRequestBuilders.post(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));
    }

    @Test
    @WithJwt("keycloak/walter.json")
    void shouldNotAddProductToBasket_WhenProductFoundIsInactive() throws Exception {
        ProductDomainModel product = new ProductDomainModel();
        product.setId(5L);
        product.setStatus(Status.INACTIVE);
        product.setKeycloakId("caedd3a9-062b-457b-94a1-b893f0b21b4e");
        product.setName("Test Product");
        product.setDescription("Test description");
        product.setPrice(150L);
        product.setQuality(Quality.GOOD);
        product.setCreatedTime(LocalDateTime.now());

        Mockito.when(productFeignClient.getProductById(5L))
                .thenReturn(product);

        final String link = "/api/basket/product/5";

        mockMvc.perform(MockMvcRequestBuilders.post(link))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof StatusException));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotAddProductToBasket_WhenUserTryToAddOwnProductToBasket() throws Exception {
        ProductDomainModel product = new ProductDomainModel();
        product.setId(5L);
        product.setStatus(Status.ACTIVE);
        product.setKeycloakId("e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88");
        product.setName("Test Product");
        product.setDescription("Test description");
        product.setPrice(150L);
        product.setQuality(Quality.GOOD);
        product.setCreatedTime(LocalDateTime.now());

        Mockito.when(productFeignClient.getProductById(5L))
                .thenReturn(product);

        final String link = "/api/basket/product/5";

        mockMvc.perform(MockMvcRequestBuilders.post(link))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedProductAdditionException));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotAddProductToBasket_WhenProductIsAlreadyInBasket() throws Exception {
        ProductDomainModel product = new ProductDomainModel();
        product.setId(1L);
        product.setStatus(Status.ACTIVE);
        product.setKeycloakId("caedd3a9-062b-457b-94a1-b893f0b21b4e");
        product.setName("Test Product");
        product.setDescription("Test description");
        product.setPrice(150L);
        product.setQuality(Quality.GOOD);
        product.setCreatedTime(LocalDateTime.now());

        Mockito.when(productFeignClient.getProductById(1L))
                .thenReturn(product);

        final String link = "/api/basket/product/1";

        mockMvc.perform(MockMvcRequestBuilders.post(link))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductInBasketException));
    }

    @Test
    @WithJwt("/keycloak/admin.json")
    void shouldRemoveProductFromBasket_AndDeleteBasket() throws Exception {
        final String link = "/api/basket/product/2";
        final String linkToGetDetails = "/api/basket/details";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").value("Removed product from basket, deleted basket."));

        mockMvc.perform(MockMvcRequestBuilders.get(linkToGetDetails))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BasketNotFoundException));
    }

    @Test
    @WithJwt("/keycloak/walter.json")
    void shouldRemoveProductFromBasket_AndBasketShouldExist() throws Exception {
        final String link = "/api/basket/product/4";
        final String linkToGetDetails = "/api/basket/details";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").value("Removed product from basket, basket sill available to use."));

        mockMvc.perform(MockMvcRequestBuilders.get(linkToGetDetails))
                .andExpect(status().isOk());
    }

    @Test
    @WithJwt("/keycloak/basketNotFound.json")
    void shouldNotRemoveProductFromBasket_WhenBasketNotFound() throws Exception {
        final String link = "/api/basket/product/4";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BasketNotFoundException));
    }

    @Test
    @WithJwt("/keycloak/walter.json")
    void shouldNotRemoveProductFromBasket_WhenProductNotFound() throws Exception {
        final String link = "/api/basket/product/99";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));
    }

    @Test
    void shouldNotRemoveProductFromBasket_WhenNoRole() throws Exception {
        final String link = "/api/basket/product/99";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isUnauthorized());
    }
}