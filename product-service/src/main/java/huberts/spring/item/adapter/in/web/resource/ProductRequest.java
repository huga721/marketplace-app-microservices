package huberts.spring.item.adapter.in.web.resource;

import huberts.spring.item.common.model.Quality;

public record ProductRequest(
        String name,
        String description,
        Long price,
        Quality quality) {
}