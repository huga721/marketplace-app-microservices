package huberts.spring.basket.adapter.out.persistance.mapper;

import huberts.spring.basket.adapter.out.persistance.entity.BasketEntity;
import huberts.spring.basket.domain.model.BasketDomainModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BasketJpaMapper {

    BasketJpaMapper BASKET_JPA_MAPPER = Mappers.getMapper(BasketJpaMapper.class);

//    BasketEntity toJpaEntity();
//    @Mapping(target = "id", source = "id")
    BasketDomainModel toDomainModel(BasketEntity basketEntity);

}
