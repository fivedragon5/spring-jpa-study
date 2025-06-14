package spring.jpa.valuetype.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import spring.jpa.valuetype.embedded.Address;
import spring.jpa.valuetype.embedded.Period;

//@Entity
public class MemberValueType {
    @Id @GeneratedValue
    private Long id;
    private String name;

    @Embedded
    private Period wordPeriod;

    @Embedded
    private Address homeAddress;
}
