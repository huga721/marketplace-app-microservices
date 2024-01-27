package huberts.spring.product.adapter.in.web.unit;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.adapter.in.web.resource.Quality;
import huberts.spring.product.application.ProductService;
import huberts.spring.product.application.exception.ProductNotFoundException;
import huberts.spring.product.application.exception.ProductStatusException;
import huberts.spring.product.domain.model.ProductDomainModel;
import huberts.spring.product.domain.model.Status;
import huberts.spring.product.domain.port.out.ProductJpaPort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    private static ProductDomainModel defaultProduct;

    @Mock
    private ProductJpaPort productJpaPort;

    @InjectMocks
    private ProductService productService;

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
    void shouldReturnEmptyList_WhenProductsAreInactive() {
        List<ProductDomainModel> products = new ArrayList<>();
        defaultProduct.setStatus(Status.INACTIVE);
        products.add(defaultProduct);

        Mockito.when(productJpaPort.getAllProducts())
                .thenReturn(products);

        List<ProductDomainModel> fromService = productService.getActiveProducts();

        assertEquals(0, fromService.size());
    }

    @Test
    void shouldReturnListOfActiveProducts() {
        ProductDomainModel inactiveProduct = new ProductDomainModel();
        inactiveProduct.setStatus(Status.INACTIVE);

        List<ProductDomainModel> products = new ArrayList<>();
        products.add(inactiveProduct);
        products.add(defaultProduct);
        products.add(defaultProduct);
        products.add(inactiveProduct);

        Mockito.when(productJpaPort.getAllProducts())
                .thenReturn(products);

        List<ProductDomainModel> fromService = productService.getActiveProducts();

        assertEquals(2, fromService.size());
    }

    @Test
    void shouldReturnAllProducts() {
        ProductDomainModel inactiveProduct = new ProductDomainModel();
        inactiveProduct.setStatus(Status.INACTIVE);

        List<ProductDomainModel> products = new ArrayList<>();
        products.add(inactiveProduct);
        products.add(defaultProduct);
        products.add(defaultProduct);
        products.add(inactiveProduct);

        Mockito.when(productJpaPort.getAllProducts())
                .thenReturn(products);

        List<ProductDomainModel> fromService = productService.getAllProducts();

        assertEquals(products.size(), fromService.size());
    }

    @Test
    void shouldReturnEmptyList_WhenNoProducts() {
        List<ProductDomainModel> products = new ArrayList<>();

        Mockito.when(productJpaPort.getAllProducts())
                .thenReturn(products);

        List<ProductDomainModel> fromService = productService.getAllProducts();

        assertEquals(products.size(), fromService.size());
    }

    @Test
    void shouldGetProductById() {
        Mockito.when(productJpaPort.getProductById(1L))
                .thenReturn(defaultProduct);

        ProductDomainModel fromService = productService.getProductById(1L);

        assertEquals(defaultProduct, fromService);
    }

    @Test
    void shouldThrowException_WhenProductWithIdDoesntExist() {
        Mockito.when(productJpaPort.getProductById(1L))
                        .thenThrow(new ProductNotFoundException(""));

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void shouldCreateProduct() {
        ProductRequest productRequest = new ProductRequest("test", "test", 100L, Quality.GOOD);

        Mockito.when(productJpaPort.saveProduct(any(ProductDomainModel.class)))
                .thenReturn(defaultProduct);

        ProductDomainModel fromService = productService.createProduct(productRequest, "123");

        assertEquals(fromService, defaultProduct);
    }

    @Test
    void shouldSetProductsAsInactive() {
        ProductDomainModel product = new ProductDomainModel();
        product.setStatus(Status.ACTIVE);

        List<ProductDomainModel> products = new ArrayList<>();
        products.add(defaultProduct);
        products.add(product);

        Mockito.when(productJpaPort.findProductsById(anyList()))
                .thenReturn(products);
        Mockito.when(productJpaPort.saveProducts(anyList()))
                .thenReturn(products);

        List<ProductDomainModel> fromService = productService.setProductsAsInactive(List.of(1L, 2L));

        assertEquals(fromService.get(0).getStatus(), Status.INACTIVE);
        assertEquals(fromService.get(1).getStatus(), Status.INACTIVE);
    }

    @Test
    void shouldNotSetProductsAsInactive_WhenProductIsInactive() {
        defaultProduct.setStatus(Status.INACTIVE);

        List<ProductDomainModel> products = new ArrayList<>();
        products.add(defaultProduct);

        Mockito.when(productJpaPort.findProductsById(anyList()))
                .thenReturn(products);

        assertThrows(ProductStatusException.class, () -> productService.setProductsAsInactive(List.of(1L)));
    }

    @Test
    void shouldEditProductById() {
        ProductRequest productRequest = new ProductRequest("test", "test", 100L, Quality.GOOD);

        Mockito.when(productJpaPort.getProductById(anyLong()))
                .thenReturn(defaultProduct);

        ProductDomainModel fromService = productService.editProductById(1L, productRequest);

        assertEquals(defaultProduct, fromService);
    }

    @Test
    void shouldNotEditProductById_WhenProductNotExist() {
        ProductRequest productRequest = new ProductRequest("test", "test", 100L, Quality.GOOD);

        Mockito.when(productJpaPort.getProductById(anyLong()))
                        .thenThrow(new ProductNotFoundException(""));

        assertThrows(ProductNotFoundException.class, () -> productService.editProductById(1L, productRequest));
    }

    @Test
    void shouldDeleteProductById() {
        Mockito.when(productJpaPort.getProductById(1L))
                .thenReturn(defaultProduct);

        Mockito.doNothing().when(productJpaPort).deleteProduct(any(ProductDomainModel.class));

        productService.deleteProductById(1L);

        verify(productJpaPort, times(1)).deleteProduct(defaultProduct);
    }

    @Test
    void shouldNotDeleteProduct_WhenProductWithIdNotExist() {
        Mockito.when(productJpaPort.getProductById(anyLong()))
                .thenThrow(new ProductNotFoundException(""));

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(1L));
    }

    @Test
    void shouldEditProductByIdAndKeycloakId() {
        ProductRequest productRequest = new ProductRequest("test", "test", 100L, Quality.GOOD);

        Mockito.when(productJpaPort.getProductByIdAndKeycloakId(anyLong(), anyString()))
                .thenReturn(defaultProduct);

        ProductDomainModel fromService = productService.editProductByIdAndKeycloakId(1L, "123", productRequest);

        assertEquals(defaultProduct, fromService);
    }

    @Test
    void shouldNotEditProduct_WhenProductNotExist() {
        ProductRequest productRequest = new ProductRequest("test", "test", 100L, Quality.GOOD);

        Mockito.when(productJpaPort.getProductByIdAndKeycloakId(anyLong(), anyString()))
                .thenThrow(new ProductNotFoundException(""));

        assertThrows(ProductNotFoundException.class, () -> productService.editProductByIdAndKeycloakId(1L, "12", productRequest));
    }

    @Test
    void shouldDeleteProductByIdAndKeycloakId() {
        Mockito.when(productJpaPort.getProductByIdAndKeycloakId(anyLong(), anyString()))
                .thenReturn(defaultProduct);

        Mockito.doNothing().when(productJpaPort).deleteProduct(any(ProductDomainModel.class));

        productService.deleteProductByIdAndKeycloakId(1L, "123");

        verify(productJpaPort, times(1)).deleteProduct(defaultProduct);
    }

    @Test
    void shouldNotDeleteProductByIdAndKeycloakId_WhenProductNotFound() {
        Mockito.when(productJpaPort.getProductByIdAndKeycloakId(anyLong(), anyString()))
                .thenThrow(new ProductNotFoundException(""));

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductByIdAndKeycloakId(1L, "123"));
    }
}