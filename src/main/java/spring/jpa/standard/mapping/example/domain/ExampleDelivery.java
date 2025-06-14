package spring.jpa.standard.mapping.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import spring.jpa.standard.mapping.example.type.ExampleStatus;

//@Entity
public class ExampleDelivery {
    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    private String city;
    private String street;
    private String zipcode;

    @Enumerated(value = EnumType.STRING)
    private ExampleStatus status;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private ExampleOrders orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public ExampleStatus getStatus() {
        return status;
    }

    public void setStatus(ExampleStatus status) {
        this.status = status;
    }

    public ExampleOrders getOrders() {
        return orders;
    }

    public void setOrders(ExampleOrders orders) {
        this.orders = orders;
    }
}
