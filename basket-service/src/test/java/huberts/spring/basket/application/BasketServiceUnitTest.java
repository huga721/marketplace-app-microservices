package huberts.spring.basket.application;

import huberts.spring.basket.adapter.out.feign.product.ProductFeignClient;
import huberts.spring.basket.adapter.out.feign.product.model.ProductDomainModel;
import huberts.spring.basket.adapter.out.feign.product.model.Status;
import huberts.spring.basket.adapter.out.feign.product.model.exception.ProductNotFoundException;
import huberts.spring.basket.application.exception.BasketNotFoundException;
import huberts.spring.basket.application.exception.ProductInBasketException;
import huberts.spring.basket.application.exception.StatusException;
import huberts.spring.basket.application.exception.UnauthorizedProductAdditionException;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;
import huberts.spring.basket.domain.port.out.BasketJpaPort;
import huberts.spring.basket.domain.port.out.BasketProductJpaPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceUnitTest {

    @Mock
    private ProductFeignClient productFeignClient;

    @Mock
    private BasketJpaPort basketJpaPort;

    @Mock
    private BasketProductJpaPort basketProductJpaPort;

    @InjectMocks
    private BasketService basketService;

    private static BasketProductDomainModel defaultBasketProduct;
    private static BasketDomainModel defaultBasket;

    @BeforeEach
    void setUp() {
        defaultBasketProduct = new BasketProductDomainModel();
        defaultBasket = new BasketDomainModel();

        defaultBasketProduct.setId(1L);
        defaultBasketProduct.setBasketId(1L);
        defaultBasketProduct.setProductId(1L);
        defaultBasketProduct.setProductValue(100L);

        defaultBasket.setId(1L);
        defaultBasket.setKeycloakId("123-123");
        defaultBasket.setBasketValue(defaultBasket.calculateBasketValue());
        defaultBasket.setProductNumber(defaultBasket.returnProductNumber());
        defaultBasket.addProduct(defaultBasketProduct);

    }

    @Test
    void shouldGetBasketById() {
        Mockito.when(basketJpaPort.getBasketById(anyLong()))
                .thenReturn(defaultBasket);

        BasketDomainModel fromService = basketService.getBasketById(1L);

        assertEquals(fromService, defaultBasket);
    }

    @Test
    void shouldNotGetBasketById_WhenBasketNotFound() {
        Mockito.when(basketJpaPort.getBasketById(anyLong()))
                .thenThrow(new BasketNotFoundException(""));

        assertThrows(BasketNotFoundException.class, () -> basketService.getBasketById(1L));
    }

    @Test
    void shouldGetBaskets() {
        List<BasketDomainModel> baskets = new ArrayList<>();
        baskets.add(defaultBasket);
        baskets.add(defaultBasket);

        Mockito.when(basketJpaPort.getAllBaskets())
                .thenReturn(baskets);

        List<BasketDomainModel> fromService = basketService.getBaskets();

        assertEquals(fromService, baskets);
    }

    @Test
    void shouldGetBasketProducts() {
        List<BasketProductDomainModel> basketProducts = new ArrayList<>();
        basketProducts.add(defaultBasketProduct);
        basketProducts.add(defaultBasketProduct);

        Mockito.when(basketProductJpaPort.getAllBasketProducts())
                .thenReturn(basketProducts);

        List<BasketProductDomainModel> fromService = basketService.getBasketProducts();

        assertEquals(fromService, basketProducts);

    }

    @Test
    void shouldGetBasketDetails() {
        Mockito.when(basketJpaPort.findBasketByKeycloakIdAndStatus(anyString(), any(Status.class)))
                .thenReturn(defaultBasket);

        BasketDomainModel fromService = basketService.getBasketDetails("123");

        assertEquals(fromService, defaultBasket);
    }

    @Test
    void shouldNotGetBasketDetails_WhenBasketNotFound() {
        assertThrows(BasketNotFoundException.class, () -> basketService.getBasketDetails("123"));
    }

    @Test
    void shouldSetBasketStatusAsInactive() {
        defaultBasket.setStatus(Status.INACTIVE);

        Mockito.when(basketJpaPort.saveBasket(any(BasketDomainModel.class)))
                .thenReturn(defaultBasket);

        BasketDomainModel fromService = basketService.setBasketInactive(new BasketDomainModel());

        assertEquals(fromService.getStatus(), Status.INACTIVE);
    }

    @Test
    void shouldCreateNewBasketAndAddProduct() {
        ProductDomainModel product = new ProductDomainModel();
        product.setStatus(Status.ACTIVE);
        product.setId(2L);
        product.setName("Test product");
        product.setPrice(100L);
        product.setDescription("Description");
        product.setKeycloakId("123-321");

        Mockito.when(basketJpaPort.findBasketByKeycloakIdAndStatus(anyString(), any(Status.class)))
                .thenReturn(null);

        Mockito.when(productFeignClient.getProductById(anyLong()))
                .thenReturn(product);

        Mockito.when(basketProductJpaPort.saveBasketProduct(any(BasketProductDomainModel.class), any(BasketDomainModel.class)))
                .thenReturn(defaultBasketProduct);

        Mockito.when(basketJpaPort.saveBasket(any(BasketDomainModel.class)))
                .thenReturn(defaultBasket);

        BasketDomainModel fromService = basketService.addProductToBasket(1L, "123");

        assertNotNull(fromService);
        assertEquals(fromService.getBasketProducts().size(), 2);
        verify(basketJpaPort, times(2)).saveBasket(any(BasketDomainModel.class));
    }

    @Test
    void shouldAddProductToExistingBasket() {
        ProductDomainModel product = new ProductDomainModel();
        product.setStatus(Status.ACTIVE);
        product.setId(2L);
        product.setName("Test product");
        product.setPrice(100L);
        product.setDescription("Description");
        product.setKeycloakId("123-321");

        Mockito.when(basketJpaPort.findBasketByKeycloakIdAndStatus(anyString(), any(Status.class)))
                .thenReturn(defaultBasket);

        Mockito.when(productFeignClient.getProductById(anyLong()))
                .thenReturn(product);

        Mockito.when(basketProductJpaPort.saveBasketProduct(any(BasketProductDomainModel.class), any(BasketDomainModel.class)))
                .thenReturn(defaultBasketProduct);

        Mockito.when(basketJpaPort.saveBasket(any(BasketDomainModel.class)))
                .thenReturn(defaultBasket);

        BasketDomainModel fromService = basketService.addProductToBasket(1L, "123");

        assertNotNull(fromService);
        assertEquals(fromService.getBasketProducts().size(), 2);
        verify(basketJpaPort, times(1)).saveBasket(any(BasketDomainModel.class));
    }

    @Test
    void shouldNotAddProductToBasket_UserCantAddOwnProductToBasket() {
        ProductDomainModel product = new ProductDomainModel();
        product.setStatus(Status.ACTIVE);
        product.setId(2L);
        product.setName("Test product");
        product.setPrice(100L);
        product.setDescription("Description");
        product.setKeycloakId("123-123");

        Mockito.when(basketJpaPort.findBasketByKeycloakIdAndStatus(anyString(), any(Status.class)))
                .thenReturn(defaultBasket);

        Mockito.when(productFeignClient.getProductById(anyLong()))
                .thenReturn(product);

        assertThrows(UnauthorizedProductAdditionException.class, () -> basketService.addProductToBasket(1L, "123"));
    }

    @Test
    void shouldNotAddProductToBasket_ProductIsAlreadyInBasket() {
        ProductDomainModel product = new ProductDomainModel();
        product.setStatus(Status.ACTIVE);
        product.setId(1L);
        product.setName("Test product");
        product.setPrice(100L);
        product.setDescription("Description");
        product.setKeycloakId("123");

        Mockito.when(basketJpaPort.findBasketByKeycloakIdAndStatus(anyString(), any(Status.class)))
                .thenReturn(defaultBasket);

        Mockito.when(productFeignClient.getProductById(anyLong()))
                .thenReturn(product);

        assertThrows(ProductInBasketException.class, () -> basketService.addProductToBasket(1L, "123"));
    }

    @Test
    void shouldNotAddProductToBasket_WhenProductNotFound() {
        ProductDomainModel product = new ProductDomainModel();
        product.setStatus(Status.ACTIVE);
        product.setId(1L);
        product.setName("Test product");
        product.setPrice(100L);
        product.setDescription("Description");
        product.setKeycloakId("123");

        Mockito.when(basketJpaPort.findBasketByKeycloakIdAndStatus(anyString(), any(Status.class)))
                .thenReturn(defaultBasket);

        Mockito.when(productFeignClient.getProductById(anyLong()))
                .thenThrow(new ProductNotFoundException(""));

        assertThrows(ProductNotFoundException.class, () -> basketService.addProductToBasket(1L, "123"));
    }

    @Test
    void shouldNotAddProductToBasket_WhenProductIsInactive() {
        ProductDomainModel product = new ProductDomainModel();
        product.setStatus(Status.INACTIVE);
        product.setId(1L);

        Mockito.when(basketJpaPort.findBasketByKeycloakIdAndStatus(anyString(), any(Status.class)))
                .thenReturn(defaultBasket);

        Mockito.when(productFeignClient.getProductById(anyLong()))
                .thenReturn(product);

        assertThrows(StatusException.class, () -> basketService.addProductToBasket(1L, "123"));
    }

    @Test
    void shouldRemoveProductFromBasket() {
        Mockito.when(basketJpaPort.findBasketByKeycloakIdAndStatus(anyString(), any(Status.class)))
                .thenReturn(defaultBasket);

        doNothing().when(basketProductJpaPort)
                .deleteBasketProduct(any(BasketProductDomainModel.class));

        Mockito.when(basketJpaPort.saveBasket(any(BasketDomainModel.class)))
                .thenReturn(defaultBasket);

        BasketDomainModel fromService = basketService.removeProductFromBasket(1L, "123");

        assertEquals(fromService.getBasketProducts().size(), 0);
    }

    @Test
    void shouldNotRemoveProductFromBasket_WhenBasketNotFound() {
        Mockito.when(basketJpaPort.findBasketByKeycloakIdAndStatus(anyString(), any(Status.class)))
                .thenReturn(null);

        assertThrows(BasketNotFoundException.class, () -> basketService.removeProductFromBasket(1L, "123"));
    }

    @Test
    void shouldNotRemoveProductFromBasket_WhenProductNotFound() {
        Mockito.when(basketJpaPort.findBasketByKeycloakIdAndStatus(anyString(), any(Status.class)))
                .thenReturn(defaultBasket);

        assertThrows(ProductNotFoundException.class, () -> basketService.removeProductFromBasket(3L, "123"));
    }
}