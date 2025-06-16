package spring.jpa.jpql.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderJpql {

    @Id @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private AddressJpql address;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private ProductJpql product;
}
