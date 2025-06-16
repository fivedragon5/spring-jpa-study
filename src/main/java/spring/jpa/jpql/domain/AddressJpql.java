package spring.jpa.jpql.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class AddressJpql {

    private String city;
    private String street;
    private String zipcode;

}
