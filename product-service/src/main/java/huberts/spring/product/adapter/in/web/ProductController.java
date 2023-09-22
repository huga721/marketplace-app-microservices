package huberts.spring.product.adapter.in.web;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.domain.model.ProductDomainModel;
import huberts.spring.product.domain.port.in.ProductServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "products", description = "Product operations")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductServicePort productServicePort;

    @Operation(summary = "[USER] Create product")
    @RolesAllowed("role-user")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ProductDomainModel createProduct(@RequestBody ProductRequest productRequest,
                                     @AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        return productServicePort.createProduct(productRequest, keycloakId);
    }

    @Operation(summary = "[USER] Get available products")
    @RolesAllowed("role-user")
    @GetMapping()
    List<ProductDomainModel> getAvailableProducts() {
        return productServicePort.getAvailableProducts();
    }

    @Operation(summary = "[USER] Get product by id")
    @RolesAllowed("role-user")
    @GetMapping("/{productId}")
    ProductDomainModel getProductById(@Parameter(description = "Id of the product")
                                      @PathVariable Long productId) {
        return productServicePort.getProductById(productId);
    }

    @Operation(summary = "[USER] Edit product by id")
    @RolesAllowed("role-user")
    @PutMapping("/edit/{productId}")
    ProductDomainModel editProductById(@Parameter(description = "Id of the product")
                                       @PathVariable Long productId,
                                       @Parameter(description = "Product request")
                                       @RequestBody ProductRequest productRequest) {
        return productServicePort.editProductById(productId, productRequest);
    }

    @Operation(summary = "[USER] Delete product by id")
    @RolesAllowed("role-user")
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProductById(@Parameter(description = "Id of the product")
                           @PathVariable Long productId) {
        productServicePort.deleteProductById(productId);
    }
}
