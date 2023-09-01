package huberts.spring.item.adapter.in.web.resource;

import huberts.spring.item.common.model.Color;
import huberts.spring.item.common.model.Quality;
import huberts.spring.item.common.model.Size;

public record ProductRequest(
        String name,
        String description,
        Long price,
        Color color,
        Quality quality,
        Size size) {
}
