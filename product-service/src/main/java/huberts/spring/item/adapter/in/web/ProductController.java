package huberts.spring.item.adapter.in.web;

import huberts.spring.item.adapter.in.web.resource.ProductRequest;
import huberts.spring.item.domain.model.ProductDomainModel;
import huberts.spring.item.domain.port.in.ProductServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "products", description = "Product operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final ProductServicePort productServicePort;

    @Operation(summary = "Create product")
    @RolesAllowed("role-user")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    ProductDomainModel createProduct(@RequestBody ProductRequest productRequest) {
        return productServicePort.createProduct(productRequest);
    }

    @Operation(summary = "Get all available products")
    @RolesAllowed("role-user")
    @GetMapping("/get-all")
    List<ProductDomainModel> getAllAvailableProducts() {
        return productServicePort.getAllAvailableProducts();
    }

    @Operation(summary = "Get product by id")
    @RolesAllowed("role-user")
    @GetMapping("/{productId}")
    ProductDomainModel getProductById(@Parameter(description = "Id of the product")
                                      @PathVariable Long productId) {
        return productServicePort.getProductById(productId);
    }

    // TODO: get item by category

    @Operation(summary = "Edit product by id")
    @RolesAllowed("role-user")
    @PutMapping("/edit/{productId}")
    ProductDomainModel editProductById(@Parameter(description = "Id of the product")
                                       @PathVariable Long productId,
                                       @Parameter(description = "Product request")
                                       @RequestBody ProductRequest productRequest) {
        return productServicePort.editProductById(productId, productRequest);
    }

    @Operation(summary = "Mark product as inactive by id")
    @RolesAllowed("role-user")
    @PutMapping("/{productId}")
    ProductDomainModel markProductInactive(@Parameter(description = "Id of the product")
                                           @PathVariable Long productId) {
        return null;
    }

    @Operation(summary = "Delete product by id")
    @RolesAllowed("role-user")
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProductById(@Parameter(description = "Id of the product")
                           @PathVariable Long productId) {
        productServicePort.deleteProductById(productId);
    }
}
