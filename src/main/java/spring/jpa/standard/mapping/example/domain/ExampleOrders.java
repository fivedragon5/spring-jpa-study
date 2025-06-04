package spring.jpa.standard.mapping.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import spring.jpa.standard.mapping.example.type.ExampleStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ExampleOrders {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private ExampleMember member;

    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private ExampleDelivery delivery;

    @OneToMany(mappedBy = "order")
    private List<ExampleOrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(value = EnumType.STRING)
    private ExampleStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExampleMember getMember() {
        return member;
    }

    public void setMember(ExampleMember member) {
        this.member = member;
    }

    public ExampleDelivery getDelivery() {
        return delivery;
    }

    public void setDelivery(ExampleDelivery delivery) {
        this.delivery = delivery;
    }

    public List<ExampleOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ExampleOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public ExampleStatus getStatus() {
        return status;
    }

    public void setStatus(ExampleStatus status) {
        this.status = status;
    }
}
