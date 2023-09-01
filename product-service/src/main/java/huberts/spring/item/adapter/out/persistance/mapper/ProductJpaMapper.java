package huberts.spring.item.adapter.out.persistance.mapper;

import huberts.spring.item.adapter.in.web.resource.ProductRequest;
import huberts.spring.item.adapter.out.persistance.entity.ProductEntity;
import huberts.spring.item.domain.model.ProductDomainModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductJpaMapper {

    ProductJpaMapper PRODUCT_JPA_MAPPER = Mappers.getMapper(ProductJpaMapper.class);

    ProductDomainModel toDomainModel(ProductEntity productEntity);

    @Mapping(target = "color", source = "productRequest.color")
    @Mapping(target = "status", constant = "ACTIVE")
    ProductEntity toJpaEntity(ProductRequest productRequest);

//    ProductEntity toJpaEntity(ProductRequest productRequest) {
//        return ProductEntity.builder()
//                .name(productRequest.name())
//                .description(productRequest.description())
//                .price(productRequest.price())
//                .color(productRequest.color())
//                .quality(productRequest.quality())
//                .size(productRequest.size())
//                .status(Status.ACTIVE)
//                .build();
//    }
//
//    ProductDomainModel toDomainEntity(ProductEntity productEntity) {
//        return ProductDomainModel.builder()
//                .id(productEntity.getId())
//                .name(productEntity.getName())
//                .description(productEntity.getDescription())
//                .price(productEntity.getPrice())
//                .color(productEntity.getColor())
//                .quality(productEntity.getQuality())
//                .size(productEntity.getSize())
//                .status(productEntity.getStatus())
//                .build();
//    }
}
