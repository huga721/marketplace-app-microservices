package huberts.spring.product.adapter.in.web.intergration;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static huberts.spring.product.adapter.in.web.resource.Quality.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminProductControllerIntegrationTest extends ContainerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private ProductEntity leatherCaseProduct;
    private ProductEntity mouseProduct;
    private ProductEntity screenProduct;
    private ProductEntity inactiveProduct;

    @BeforeAll
    public void init() {
        leatherCaseProduct = new ProductEntity();
        leatherCaseProduct.setId(1L);
        leatherCaseProduct.setName("Leather Case");
        leatherCaseProduct.setKeycloakId("cbe10031-5ab7-4ff6-b740-e9b001d93dd1");
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

        screenProduct = new ProductEntity();
        screenProduct.setId(3L);
        screenProduct.setName("TV Screen");
        screenProduct.setKeycloakId("e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88");
        screenProduct.setDescription("PC Mouse in new condition");
        screenProduct.setPrice(500L);
        screenProduct.setCreatedTime(LocalDateTime.now());
        screenProduct.setQuality(Quality.NEW);
        screenProduct.setStatus(Status.ACTIVE);

        inactiveProduct = new ProductEntity();
        inactiveProduct.setId(4L);
        inactiveProduct.setName("Inactive product");
        inactiveProduct.setKeycloakId("ae885313-4d31-4682-8360-09456c7ac312");
        inactiveProduct.setDescription("Inactive product description");
        inactiveProduct.setPrice(1234L);
        inactiveProduct.setCreatedTime(LocalDateTime.now());
        inactiveProduct.setQuality(Quality.SATISFACTORY);
        inactiveProduct.setStatus(Status.INACTIVE);

        productRepository.save(leatherCaseProduct);
        productRepository.save(mouseProduct);
        productRepository.save(screenProduct);
        productRepository.save(inactiveProduct);
    }

    @Test
    @WithJwt("admin.json")
    void shouldGetAllProducts() throws Exception {
        final String link = "/api/product/admin";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(leatherCaseProduct.getName()))
                .andExpect(jsonPath("$[1].name").value(mouseProduct.getName()))
                .andExpect(jsonPath("$[2].name").value(screenProduct.getName()))
                .andExpect(jsonPath("$[3].name").value(inactiveProduct.getName()));
    }

    @Test
    @WithJwt("speedy.json")
    void shouldNotGetALlProducts_WhenRequestedByUserRole() throws Exception {
        final String link = "/api/product/admin";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithJwt("admin.json")
    void shouldEditProductById() throws Exception {
        ProductRequest productRequest = new ProductRequest("changedName", "changedDescription", 1L, SATISFACTORY);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product/admin/3";

        mockMvc.perform(MockMvcRequestBuilders.get(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productRequest.name()))
                .andExpect(jsonPath("$.description").value(productRequest.description()))
                .andExpect(jsonPath("$.price").value(productRequest.price()))
                .andExpect(jsonPath("$.quality").value(productRequest.quality().toString()));
    }

    @Test
    @WithJwt("admin.json")
    void shouldNotEditProduct_WhenProductNotFound() throws Exception {
        ProductRequest productRequest = new ProductRequest("changedName", "changedDescription", 1L, SATISFACTORY);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product/admin/999";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result instanceof ProductNotFoundException));
    }

    @Test
    @WithJwt("speedy.json")
    void shouldNotEditProduct_WhenRequestedByUserRole() throws Exception {
        ProductRequest productRequest = new ProductRequest("changedName", "changedDescription", 1L, SATISFACTORY);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithJwt("admin.json")
    void shouldNotEditProduct_WhenFieldsInRequestBodyAreEmpty() throws Exception {
        ProductRequest productRequest = new ProductRequest("dsa", "", 3L, SATISFACTORY);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result instanceof MethodArgumentNotValidException));
    }
}