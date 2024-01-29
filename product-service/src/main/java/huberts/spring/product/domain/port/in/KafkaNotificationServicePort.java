package huberts.spring.product.domain.port.in;

import huberts.spring.product.domain.model.ProductDomainModel;

import java.util.List;

public interface KafkaNotificationServicePort {
    void sendProductCreatedNotificationEvent(ProductDomainModel productDomainModel);

    void sendProductInactiveNotificationEvent(List<ProductDomainModel> productDomainModel);

    void sendProductEditedNotificationEvent(ProductDomainModel productDomainModel);

    void sendProductDeletedNotificationEvent(ProductDomainModel productDomainModel);
}
