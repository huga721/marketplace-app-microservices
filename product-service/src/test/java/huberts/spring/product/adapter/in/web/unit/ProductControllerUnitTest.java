package huberts.spring.product.adapter.in.web.unit;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.product.adapter.in.web.ProductController;
import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.adapter.in.web.resource.Quality;
import huberts.spring.product.application.exception.ProductNotFoundException;
import huberts.spring.product.domain.model.ProductDomainModel;
import huberts.spring.product.domain.model.Status;
import huberts.spring.product.domain.port.in.ProductServicePort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

@ComponentScan(basePackages = {"huberts.spring.product.application.security"})
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerUnitTest {

    private static ProductDomainModel defaultProduct;

    @MockBean
    private ProductServicePort productServicePort;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void init() {
        defaultProduct = new ProductDomainModel();
        defaultProduct.setId(1L);
        defaultProduct.setName("Leather Case");
        defaultProduct.setPrice(100L);
        defaultProduct.setQuality(Quality.SATISFACTORY.name());
        defaultProduct.setStatus(Status.ACTIVE);
        defaultProduct.setDescription("Just a leather case with satisfactory quality");
        defaultProduct.setKeycloakId("123-3213-32412-4123");
    }

    @Test
    void shouldReturnActiveProductsProductList() throws Exception {
        List<ProductDomainModel> products = new ArrayList<>();
        products.add(defaultProduct);
        products.add(defaultProduct);

        Mockito.when(productServicePort.getActiveProducts())
                .thenReturn(products);

        final String link = "/api/product";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(products.size())));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldReturnActiveProductList_WithUserRole() throws Exception {
        List<ProductDomainModel> products = new ArrayList<>();
        products.add(defaultProduct);

        Mockito.when(productServicePort.getActiveProducts())
                .thenReturn(products);

        final String link = "/api/product";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(products.size())));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldReturnEmptyList_WhenNoProductsFound() throws Exception {
        List<ProductDomainModel> products = new ArrayList<>();

        Mockito.when(productServicePort.getActiveProducts())
                .thenReturn(products);

        final String link = "/api/product";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(products.size())));
    }

    @Test
    void shouldGetProductById_WithNoRole() throws Exception {
        Mockito.when(productServicePort.getProductById(1L))
                .thenReturn(defaultProduct);

        final String link = "/api/product/1";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(defaultProduct.getName()))
                .andExpect(jsonPath("$.price").value(defaultProduct.getPrice()))
                .andExpect(jsonPath("$.status").value(defaultProduct.getStatus()));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldGetProductById_WithUserRole() throws Exception {
        Mockito.when(productServicePort.getProductById(1L))
                .thenReturn(defaultProduct);

        final String link = "/api/product/1";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowException_WhenProductNotFound() throws Exception {
        Mockito.when(productServicePort.getProductById(1L))
                .thenThrow(new ProductNotFoundException(""));

        final String link = "/api/product/1";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldCreateProduct_WithUserRole() throws Exception {
        ProductRequest productRequest = new ProductRequest("test", "test", 100L, Quality.GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        Mockito.when(productServicePort.createProduct(any(ProductRequest.class), anyString()))
                .thenReturn(defaultProduct);

        final String link = "/api/product";

        mockMvc.perform(MockMvcRequestBuilders.post(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(defaultProduct.getName()))
                .andExpect(jsonPath("$.price").value(defaultProduct.getPrice()))
                .andExpect(jsonPath("$.description").value(defaultProduct.getDescription()))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void shouldNotCreateProduct_WithNoRole() throws Exception {
        ProductRequest productRequest = new ProductRequest("test", "test", 100L, Quality.GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product";

        mockMvc.perform(MockMvcRequestBuilders.post(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldGetAllProductsOfAuthenticatedUser() throws Exception {
        Mockito.when(productServicePort.getProductsByKeycloakId(anyString()))
                .thenReturn(List.of(defaultProduct, defaultProduct));

        final String link = "/api/product/user";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldNotGetProductsOfAuthenticatedUser_WhenNoJwtAuthenticated() throws Exception {
        final String link = "/api/product/user";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldEditProductByIdAndKeycloakId_WithUserRole() throws Exception {
        ProductRequest productRequest = new ProductRequest("test", "test", 100L, Quality.GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        Mockito.when(productServicePort.editProductByIdAndKeycloakId(anyLong(), anyString(), any(ProductRequest.class)))
                .thenReturn(defaultProduct);

        final String link = "/api/product/3";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(defaultProduct.getName()))
                .andExpect(jsonPath("$.description").value(defaultProduct.getDescription()));
    }

    @Test
    void shouldNotEditProduct_WithNoRole() throws Exception {
        ProductRequest productRequest = new ProductRequest("test", "test", 100L, Quality.GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product/3";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldDeleteProductByIdAndKeycloakId_WithUserRole() throws Exception {
        Mockito.doNothing().when(productServicePort).deleteProductById(anyLong());

        final String link = "/api/product/3";

        mockMvc.perform(MockMvcRequestBuilders.patch(link))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteProductByIdAndKeycloakId_WithNoRole() throws Exception {
        Mockito.doNothing().when(productServicePort).deleteProductById(anyLong());

        final String link = "/api/product/3";

        mockMvc.perform(MockMvcRequestBuilders.patch(link))
                .andExpect(status().isUnauthorized());
    }
}