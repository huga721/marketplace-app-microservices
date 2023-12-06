package huberts.spring.product.adapter.out.persistance.mapper;

import huberts.spring.product.adapter.out.persistance.entity.ProductEntity;
import huberts.spring.product.domain.model.ProductDomainModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductJpaMapper {

    ProductJpaMapper PRODUCT_JPA_MAPPER = Mappers.getMapper(ProductJpaMapper.class);

    ProductDomainModel toDomainModel(ProductEntity productEntity);

    ProductEntity toJpaEntity(ProductDomainModel productDomain);
}