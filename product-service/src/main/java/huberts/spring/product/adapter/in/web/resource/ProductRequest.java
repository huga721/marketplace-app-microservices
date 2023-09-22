package huberts.spring.product.adapter.in.web.resource;

import huberts.spring.product.common.model.Quality;

public record ProductRequest(
        String name,
        String description,
        Long price,
        Quality quality) {
}