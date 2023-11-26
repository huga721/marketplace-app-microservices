package huberts.spring.product.adapter.in.web;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.domain.model.ProductDomainModel;
import huberts.spring.product.domain.port.in.ProductServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "product", description = "Product REST API")
@Validated
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductServicePort productServicePort;

    @GetMapping()
    List<ProductDomainModel> getActiveProducts() {
        LOGGER.info(">> ProductController: getting active products");
        return productServicePort.getActiveProducts();
    }

    @GetMapping("/{productId}")
    ProductDomainModel getProductById(@PathVariable Long productId) {
        LOGGER.info(">> ProductController: getting product by id: {}", productId);
        return productServicePort.getProductById(productId);
    }

    @RolesAllowed("role-user")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ProductDomainModel createProduct(@Valid @RequestBody ProductRequest productRequest, @AuthenticationPrincipal Jwt jwt) {
        LOGGER.info(">> ProductController: creating product: {}", productRequest);
        String keycloakId = jwt.getSubject();
        return productServicePort.createProduct(productRequest, keycloakId);
    }

    @RolesAllowed("role-user")
    @GetMapping("/user")
    List<ProductDomainModel> getAuthenticatedUserProducts(@AuthenticationPrincipal Jwt jwt) {
        LOGGER.info(">> ProductController: getting products of current authenticated user");
        String keycloakId = jwt.getSubject();
        return productServicePort.getProductsByKeycloakId(keycloakId);
    }

    @RolesAllowed("role-user")
    @PatchMapping("/{productId}")
    ProductDomainModel editProductByIdAndKeycloakId(@PathVariable Long productId, @Valid @RequestBody ProductRequest productRequest,
                                       @AuthenticationPrincipal Jwt jwt) {
        LOGGER.info(">> ProductController: editing product by id: {} and keycloak id: {}", productId, jwt.getSubject());
        String keycloakId = jwt.getSubject();
        return productServicePort.editProductByIdAndKeycloakId(productId, keycloakId, productRequest);
    }

    @RolesAllowed("role-user")
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProductByIdAndKeycloakId(@PathVariable Long productId, @AuthenticationPrincipal Jwt jwt) {
        LOGGER.info(">> ProductController: deleting product by id: {} and keycloak id: {}", productId, jwt.getSubject());
        String keycloakId = jwt.getSubject();
        productServicePort.deleteProductByIdAndKeycloakId(productId, keycloakId);
    }
}