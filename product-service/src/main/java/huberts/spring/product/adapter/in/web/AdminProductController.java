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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "product-admin", description = "Product Admin REST API")
@Validated
@RestController
@RequestMapping("/api/product/admin")
@RequiredArgsConstructor
public class AdminProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminProductController.class);
    private final ProductServicePort productServicePort;

    @RolesAllowed("role-admin")
    @GetMapping()
    List<ProductDomainModel> getAllProducts() {
        LOGGER.info(">> AdminProductController: getting all products can be active or inactive");
        return productServicePort.getAllProducts();
    }

    @RolesAllowed("role-admin")
    @PatchMapping("/{productId}")
    ProductDomainModel editProductById(@PathVariable Long productId, @Valid @RequestBody ProductRequest productRequest) {
        LOGGER.info(">> AdminProductController: editing product: {} by id: {}", productRequest, productId);
        return productServicePort.editProductById(productId, productRequest);
    }

    @RolesAllowed("role-admin")
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProductById(@PathVariable Long productId) {
        LOGGER.info(">> AdminProductController: deleting product by id: {}", productId);
        productServicePort.deleteProductById(productId);
    }
}