package huberts.spring.order.domain.model;

import huberts.spring.order.adapter.in.web.resource.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDomainModel {

    private Long id;
    private String keycloakId;
    private Long basketId;
    private PaymentType paymentType;
    private String address;
    private String postalCode;

    public void setPaymentType(PaymentType paymentType) {
//        switch (paymentType.equalsIgnoreCase(""))
    }
}