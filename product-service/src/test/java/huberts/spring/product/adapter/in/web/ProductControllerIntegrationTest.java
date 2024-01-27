package huberts.spring.product.adapter.in.web.in.integration;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.product.ContainerIT;
import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.adapter.out.persistance.entity.ProductEntity;
import huberts.spring.product.adapter.out.persistance.entity.Quality;
import huberts.spring.product.adapter.out.persistance.entity.Status;
import huberts.spring.product.adapter.out.persistance.repository.ProductRepository;
import huberts.spring.product.application.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;

import static huberts.spring.product.adapter.in.web.resource.Quality.GOOD;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerIntegrationTest extends ContainerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private ProductEntity leatherCaseProduct;
    private ProductEntity mouseProduct;
    private ProductEntity productToDelete;
    private ProductEntity inactiveProduct;
    private ProductEntity productToEdit;

    @BeforeAll
    public void init() {
        leatherCaseProduct = new ProductEntity();
        leatherCaseProduct.setId(1L);
        leatherCaseProduct.setName("Leather Case");
        leatherCaseProduct.setKeycloakId("e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88");
        leatherCaseProduct.setDescription("Leather Case in good condition");
        leatherCaseProduct.setPrice(39L);
        leatherCaseProduct.setCreatedTime(LocalDateTime.now());
        leatherCaseProduct.setQuality(Quality.GOOD);
        leatherCaseProduct.setStatus(Status.ACTIVE);

        mouseProduct = new ProductEntity();
        mouseProduct.setId(2L);
        mouseProduct.setName("PC Mouse");
        mouseProduct.setKeycloakId("caedd3a9-062b-457b-94a1-b893f0b21b4e");
        mouseProduct.setDescription("PC Mouse in satisfactory condition");
        mouseProduct.setPrice(30L);
        mouseProduct.setCreatedTime(LocalDateTime.now());
        mouseProduct.setQuality(Quality.SATISFACTORY);
        mouseProduct.setStatus(Status.ACTIVE);

        productToDelete = new ProductEntity();
        productToDelete.setId(3L);
        productToDelete.setName("TV Screen");
        productToDelete.setKeycloakId("e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88");
        productToDelete.setDescription("PC Mouse in new condition");
        productToDelete.setPrice(500L);
        productToDelete.setCreatedTime(LocalDateTime.now());
        productToDelete.setQuality(Quality.NEW);
        productToDelete.setStatus(Status.ACTIVE);

        inactiveProduct = new ProductEntity();
        inactiveProduct.setId(4L);
        inactiveProduct.setName("Inactive product");
        inactiveProduct.setKeycloakId("e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88");
        inactiveProduct.setDescription("Inactive product description");
        inactiveProduct.setPrice(1234L);
        inactiveProduct.setCreatedTime(LocalDateTime.now());
        inactiveProduct.setQuality(Quality.SATISFACTORY);
        inactiveProduct.setStatus(Status.INACTIVE);

        productToEdit = new ProductEntity();
        productToEdit.setId(5L);
        productToEdit.setName("Edit product");
        productToEdit.setKeycloakId("e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88");
        productToEdit.setDescription("Edit product description");
        productToEdit.setPrice(12334L);
        productToEdit.setCreatedTime(LocalDateTime.now());
        productToEdit.setQuality(Quality.SATISFACTORY);
        productToEdit.setStatus(Status.ACTIVE);

        productRepository.save(leatherCaseProduct);
        productRepository.save(mouseProduct);
        productRepository.save(productToDelete);
        productRepository.save(inactiveProduct);
        productRepository.save(productToEdit);
    }

    @Test
    void shouldGetAllActiveProduct_WithNoRole() throws Exception {
        final String link = "/api/product";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value(leatherCaseProduct.getName()))
                .andExpect(jsonPath("$[1].name").value(mouseProduct.getName()))
                .andExpect(jsonPath("$[2].id").value(3));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldGetAllActiveProduct_WithUserRole() throws Exception {
        final String link = "/api/product";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value(leatherCaseProduct.getName()))
                .andExpect(jsonPath("$[1].name").value(mouseProduct.getName()));
    }

    @Test
    void shouldGetProductById_WithNoRole() throws Exception {
        final String link = "/api/product/1";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(leatherCaseProduct.getName()));
    }

    @Test
    void shouldGetProductById_WithUserRole() throws Exception {
        final String link = "/api/product/1";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(leatherCaseProduct.getName()));
    }

    @Test
    void shouldNotGetProductById_WhenProductNotExist() throws Exception {
        final String link = "/api/product/999";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldCreateProduct_WithUserRole() throws Exception {
        ProductRequest productRequest = new ProductRequest("product", "description", 10L, GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String linkToCreate = "/api/product";
        final String linkToGet = "/api/product/6";

        mockMvc.perform(MockMvcRequestBuilders.post(linkToCreate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productRequest.name()))
                .andExpect(jsonPath("$.description").value(productRequest.description()))
                .andExpect(jsonPath("$.price").value(productRequest.price()))
                .andExpect(jsonPath("$.quality").value(productRequest.quality().toString()))
                .andExpect(jsonPath("$.id").value(6));

        mockMvc.perform(MockMvcRequestBuilders.get(linkToGet))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productRequest.name()));
    }

    @Test
    void shouldNotCreateProduct_WithNoRole() throws Exception {
        ProductRequest productRequest = new ProductRequest("product", "description", 10L, GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product";

        mockMvc.perform(MockMvcRequestBuilders.post(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldNotCreateProduct_WhenFieldsInRequestAreEmpty() throws Exception {
        ProductRequest productRequest = new ProductRequest("", "", 10L, GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product";

        mockMvc.perform(MockMvcRequestBuilders.post(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void shouldNotCreateProduct_WhenPriceIsLessThan1() throws Exception {
        ProductRequest productRequest = new ProductRequest("dawda", "dadw", 0L, GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product";

        mockMvc.perform(MockMvcRequestBuilders.post(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldGetProductsOfAuthenticatedUser() throws Exception {
        String userKeycloakId = "e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88";

        final String link = "/api/product/user";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].keycloakId").value(userKeycloakId))
                .andExpect(jsonPath("$[0].name").value(leatherCaseProduct.getName()));
    }

    @Test
    void shouldNotGetProductsOfAuthenticatedUser_WhenNoRole() throws Exception {
        final String link = "/api/product/user";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldEditProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest("test", "test", 10L, GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product/5";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value(productRequest.name()))
                .andExpect(jsonPath("$.description").value(productRequest.description()))
                .andExpect(jsonPath("$.price").value(productRequest.price()))
                .andExpect(jsonPath("$.quality").value(productRequest.quality().name()));
    }

    @Test
    void shouldNotEditProduct_WithNoRole() throws Exception {
        ProductRequest productRequest = new ProductRequest("test", "test", 10L, GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product/5";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotEditProduct_WhenFieldsInRequestBodyAreEmpty() throws Exception {
        ProductRequest productRequest = new ProductRequest("", "test", 10L, GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product/5";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotEditProduct_WhenPriceInRequestIsLessThan1() throws Exception {
        ProductRequest productRequest = new ProductRequest("test", "test", 0L, GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product/5";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldDeleteProduct() throws Exception {
        final String link = "/api/product/3";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));
    }

    @Test
    void shouldNotDeleteProduct_WhenNoRole() throws Exception {
        final String link = "/api/product/4";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotDeleteProduct_WhenProductNotFound() throws Exception {
        final String link = "/api/product/999";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));
    }
}