package huberts.spring.product.application.mapper;

import huberts.spring.product.adapter.out.persistance.entity.ProductEntity;
import huberts.spring.product.domain.model.ProductDomainModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface ProductMapper {

    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDomainModel toDomainModel(ProductEntity productEntity);

    ProductEntity toJpaEntity(ProductDomainModel productDomain);
}