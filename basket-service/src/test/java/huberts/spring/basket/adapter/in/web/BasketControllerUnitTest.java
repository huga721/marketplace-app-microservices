package huberts.spring.basket.adapter.in.web;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import huberts.spring.basket.application.exception.BasketNotFoundException;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;
import huberts.spring.basket.domain.port.in.BasketServicePort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = {"huberts.spring.basket.application.security"})
@ExtendWith(SpringExtension.class)
@WebMvcTest(BasketController.class)
class BasketControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketServicePort basketServicePort;

    private static BasketProductDomainModel defaultBasketProduct;
    private static BasketDomainModel defaultBasket;

    @BeforeAll
    public static void init() {
        defaultBasketProduct = new BasketProductDomainModel();
        defaultBasket = new BasketDomainModel();

        defaultBasketProduct.setId(1L);
        defaultBasketProduct.setBasketId(1L);
        defaultBasketProduct.setProductId(1L);
        defaultBasketProduct.setProductValue(100L);

        defaultBasket.setId(1L);
        defaultBasket.setBasketProducts(List.of(defaultBasketProduct));
        defaultBasket.setKeycloakId("123-123");
        defaultBasket.setBasketValue(defaultBasket.calculateBasketValue());
        defaultBasket.setProductNumber(defaultBasket.returnProductNumber());
    }

    @Test
    void shouldReturnAllBaskets() throws Exception {
        List<BasketDomainModel> baskets = new ArrayList<>();
        baskets.add(defaultBasket);
        baskets.add(defaultBasket);

        Mockito.when(basketServicePort.getBaskets())
                .thenReturn(baskets);

        final String link = "/api/basket";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(baskets.size())));
    }

    @Test
    void shouldGetBasketById() throws Exception {
        Mockito.when(basketServicePort.getBasketById(anyLong()))
                .thenReturn(defaultBasket);

        final String link = "/api/basket/1";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(defaultBasket.getId()))
                .andExpect(jsonPath("$.basketValue").value(100L))
                .andExpect(jsonPath("$.status").value(defaultBasket.getStatus().name()))
                .andExpect(jsonPath("$.productNumber").value(1));
    }

    @Test
    void shouldNotGetBasket_WhenBasketNotFound() throws Exception {
        Mockito.when(basketServicePort.getBasketById(anyLong()))
                .thenThrow(new BasketNotFoundException(""));

        final String link = "/api/basket/432";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BasketNotFoundException));
    }

    @Test
    void shouldGetAllProductsThatAreInBaskets() throws Exception {
        List<BasketProductDomainModel> basketProducts = new ArrayList<>();
        basketProducts.add(defaultBasketProduct);
        basketProducts.add(defaultBasketProduct);

        final String link = "/api/basket/product";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(basketProducts.size())));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldGetBasketDetailsOfCurrentAuthUser() throws Exception {
        Mockito.when(basketServicePort.getBasketDetails(anyString()))
                .thenReturn(defaultBasket);

        final String link = "/api/basket/details";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(defaultBasket.getId()))
                .andExpect(jsonPath("$.basketValue").value(100L))
                .andExpect(jsonPath("$.status").value(defaultBasket.getStatus().name()))
                .andExpect(jsonPath("$.productNumber").value(1));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldThrowException_WhenUserDontHaveABasket() throws Exception {
        Mockito.when(basketServicePort.getBasketDetails(anyString()))
                .thenThrow(new BasketNotFoundException(""));

        final String link = "/api/basket/details";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BasketNotFoundException));
    }

    @Test
    void shouldNotReturnBasketDetails_WhenNoRole() throws Exception {
        final String link = "/api/basket/details";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldAddProductToBasket() throws Exception {
        Mockito.when(basketServicePort.addProductToBasket(anyLong(), anyString()))
                .thenReturn(defaultBasket);

        final String link = "/api/basket/product/1";

        mockMvc.perform(MockMvcRequestBuilders.post(link))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(defaultBasket.getId()))
                .andExpect(jsonPath("$.basketValue").value(100L))
                .andExpect(jsonPath("$.status").value(defaultBasket.getStatus().name()))
                .andExpect(jsonPath("$.productNumber").value(1));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotAddProductToBasket_WhenProductNotFound() throws Exception {
        Mockito.when(basketServicePort.addProductToBasket(anyLong(), anyString()))
                .thenThrow(new BasketNotFoundException(""));

        final String link = "/api/basket/product/1";

        mockMvc.perform(MockMvcRequestBuilders.post(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BasketNotFoundException));
    }

    @Test
    void shouldNotAddProductToBasket_WhenNoRole() throws Exception {
        final String link = "/api/basket/product/1";

        mockMvc.perform(MockMvcRequestBuilders.post(link))
                .andExpect(status().isUnauthorized());
    }
}