package huberts.spring.product.adapter.in.web.resource;

public record ProductRequest(
        String name,
        String description,
        Long price,
        Quality quality) {
}