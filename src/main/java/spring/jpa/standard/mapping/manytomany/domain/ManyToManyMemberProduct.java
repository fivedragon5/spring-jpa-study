package spring.jpa.standard.mapping.manytomany.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 다대다 정리
 *  1. 다대다 한계 극복
 *      - 다대다 관계는 중간 테이블을 통해서 다대일, 일대다 관계로 풀어내는것이 좋음
 *      - @ManyToMany -> @ManyToOne, @OneToMany로 변경
 */
//@Entity
public class ManyToManyMemberProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private ManyToManyMember member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private ManyToManyProduct product;
}
