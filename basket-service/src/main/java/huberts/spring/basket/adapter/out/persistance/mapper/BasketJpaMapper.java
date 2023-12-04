package huberts.spring.basket.adapter.out.persistance.mapper;

import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import huberts.spring.basket.adapter.out.persistance.entity.BasketProductEntity;
import huberts.spring.basket.domain.model.BasketDomainModel;
import huberts.spring.basket.domain.model.BasketProductDomainModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(builder = @Builder(disableBuilder = true))
public interface BasketJpaMapper {

    BasketJpaMapper BASKET_JPA_MAPPER = Mappers.getMapper(BasketJpaMapper.class);

    @Mapping(target = "basketProducts", source = "basketProducts", qualifiedByName = "mapBasketProductsEntity")
    BasketDomainModel toDomainModel(BasketEntity basketEntity);

    @Named("mapBasketProductsEntity")
    default List<BasketProductDomainModel> mapList(List<BasketProductEntity> basketProducts) {
        return basketProducts.stream()
                .map(product -> {
                    BasketProductDomainModel basketProduct = new BasketProductDomainModel();
                    basketProduct.setId(product.getId());
                    basketProduct.setProductId(product.getProductId());
                    basketProduct.setProductValue(product.getProductValue());
                    basketProduct.setBasketId(product.getBasketEntity().getId());
                    return basketProduct;
                })
                .collect(Collectors.toList());
    }

    @Mapping(target = "basketProducts", ignore = true)
    @Mapping(target = "productNumber", source = "basketProducts", qualifiedByName = "getProductAmount")
    @Mapping(target = "basketValue", source = "basketProducts", qualifiedByName = "calculateBasketValue")
    BasketEntity toJpaEntity(BasketDomainModel basketDomainModel);

    @AfterMapping
    default void toJpaEntity(BasketDomainModel basketDomainModel,
                             @MappingTarget final BasketEntity basketEntity) {
        basketEntity.setBasketProducts(basketDomainModel.getBasketProducts().stream()
                .map(product -> {
                    BasketProductEntity productEntity = new BasketProductEntity();
                    productEntity.setId(product.getId());
                    productEntity.setProductId(product.getProductId());
                    productEntity.setProductValue(product.getProductValue());
                    productEntity.setBasketEntity(basketEntity);
                    return productEntity;
                })
                .collect(Collectors.toList()));
    }

    @Named("getProductAmount")
    default Integer getProductAmount(List<BasketProductDomainModel> basketProducts) {
        return basketProducts.size();
    }

    @Named("calculateBasketValue")
    default Long calculateBasketValue(List<BasketProductDomainModel> basketProducts) {
        return basketProducts.stream()
                .map(BasketProductDomainModel::getProductValue)
                .mapToLong(Long::intValue)
                .sum();
    }
}