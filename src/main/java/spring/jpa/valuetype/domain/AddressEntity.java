package spring.jpa.valuetype.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import spring.jpa.valuetype.domain.embedded.Address;

//@Entity
@Table(name = "ADDRESS")
@Getter
@Setter
public class AddressEntity {
    @Id @GeneratedValue
    private Long id;
    private Address address;

    public AddressEntity(String city, String street, String zipcode) {
        this.address = new Address(city, street, zipcode);
    }

    public AddressEntity() {
    }
}
