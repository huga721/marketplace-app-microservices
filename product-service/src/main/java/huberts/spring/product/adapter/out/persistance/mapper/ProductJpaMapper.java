package huberts.spring.product.adapter.out.persistance.mapper;

import huberts.spring.product.adapter.in.web.resource.ProductRequest;
import huberts.spring.product.adapter.out.persistance.entity.ProductEntity;
import huberts.spring.product.domain.model.ProductDomainModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductJpaMapper {

    ProductJpaMapper PRODUCT_JPA_MAPPER = Mappers.getMapper(ProductJpaMapper.class);

    ProductDomainModel toDomainModel(ProductEntity productEntity);

    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "keycloakId", source = "keycloakId")
    ProductEntity toJpaEntity(ProductRequest productRequest, String keycloakId);

}
