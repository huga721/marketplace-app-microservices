package huberts.spring.product.adapter.in.web.in.unit;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.product.adapter.in.web.AdminProductController;
import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.adapter.in.web.resource.Quality;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = {"huberts.spring.product.application.security"})
@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminProductController.class)
public class AdminProductControllerUnitTest {

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
    @WithJwt("keycloak/admin.json")
    void shouldGetAllProducts_WithAdminRole() throws Exception {
        Mockito.when(productServicePort.getAllProducts())
                .thenReturn(List.of(defaultProduct, defaultProduct));

        final String link = "/api/product/admin";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotGetAllProducts_WithUserRole() throws Exception {
        final String link = "/api/product/admin";

        mockMvc.perform(MockMvcRequestBuilders.get(link))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithJwt("keycloak/admin.json")
    void shouldEditProductById_WithAdminRole() throws Exception {
        ProductRequest productRequest = new ProductRequest("test", "test", 100L, Quality.GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        Mockito.when(productServicePort.editProductById(anyLong(), any(ProductRequest.class)))
                .thenReturn(defaultProduct);

        final String link = "/api/product/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(defaultProduct.getName()))
                .andExpect(jsonPath("$.description").value(defaultProduct.getDescription()))
                .andExpect(jsonPath("$.price").value(defaultProduct.getPrice()));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotEditProduct_WithUserRole() throws Exception {
        ProductRequest productRequest = new ProductRequest("test", "test", 100L, Quality.GOOD);
        String productRequestAsString = objectMapper.writeValueAsString(productRequest);

        final String link = "/api/product/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestAsString))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithJwt("keycloak/admin.json")
    void shouldDeleteProduct_WithAdminRole() throws Exception {
        final String link = "/api/product/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotDeleteProduct_WithUserRole() throws Exception {
        final String link = "/api/product/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isForbidden());
    }
}