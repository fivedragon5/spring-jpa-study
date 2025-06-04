package spring.jpa.standard.mapping.manytomany.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ManyToManyProduct {
    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "product")
    private List<ManyToManyMemberProduct> memberProducts = new ArrayList<>();

//    @ManyToMany(mappedBy = "products")
//    private List<Member> members = new ArrayList<>();
}
