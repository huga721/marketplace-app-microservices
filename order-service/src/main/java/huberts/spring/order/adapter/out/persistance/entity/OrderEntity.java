package huberts.spring.order.adapter.out.persistance.entity;

import huberts.spring.order.adapter.in.web.resource.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keycloakId;

    private Long basketId;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String address;
    private String postalCode;
}
