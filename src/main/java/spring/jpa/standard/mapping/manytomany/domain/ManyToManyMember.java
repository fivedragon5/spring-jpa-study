package spring.jpa.standard.mapping.manytomany.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

//@Entity
public class ManyToManyMember {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;

    @OneToMany(mappedBy = "member")
    private List<ManyToManyMemberProduct> memberProducts = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT")
//    private List<ManyToManyProduct> products = new ArrayList<>();
}
