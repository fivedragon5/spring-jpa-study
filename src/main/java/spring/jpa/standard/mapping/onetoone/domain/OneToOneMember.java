package spring.jpa.standard.mapping.onetoone.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * 일대일 정리
 *  1. 주 테이블에 외래키가 있는 경우
 *   - 객체지향 개발자 선호
 *   - JPA 매핑 편리
 *   - 주 테이블만 조회해서 대상 테이블에 데이터가 있는지 확인 가능
 *   - 값이 없으면 외래키에 null 허용
 *  2. 대상 테이블에 외래키가 있는 경우
 *   - 전통적인 데이터베이스 개발자 선호
 *   - 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
 *   - 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩됨
 */
//@Entity
public class OneToOneMember {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private OneToOneLocker locker;
}
