package huberts.spring.basket.adapter.out.persistance.mapper;

import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import huberts.spring.basket.adapter.out.persistance.entity.BasketProductEntity;
import huberts.spring.basket.domain.model.BasketProductDomainModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BasketProductJpaMapper {

    BasketProductJpaMapper BASKET_PRODUCT_JPA_MAPPER = Mappers.getMapper(BasketProductJpaMapper.class);

    @Mapping(target = "id", source = "basketProduct.id")
    @Mapping(target = "basketEntity", source = "basket")
    BasketProductEntity toJpaEntity(BasketProductDomainModel basketProduct, BasketEntity basket);

    @Mapping(target = "basketId", source = ".", qualifiedByName = "getBasketId")
    BasketProductDomainModel toDomainModel(BasketProductEntity basketProductEntity);

    @Named("getBasketId")
    default Long getBasketId(BasketProductEntity basketProductEntity) {
        return basketProductEntity.getBasketEntity().getId();
    }
}