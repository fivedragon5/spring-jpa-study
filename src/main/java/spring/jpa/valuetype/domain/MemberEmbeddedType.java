package spring.jpa.valuetype.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import spring.jpa.valuetype.domain.embedded.Address;
import spring.jpa.valuetype.domain.embedded.Period;

//@Entity
public class MemberEmbeddedType {
    @Id @GeneratedValue
    private Long id;
    private String name;

    @Embedded
    private Period wordPeriod;

    @Embedded
    private Address homeAddress;
}
