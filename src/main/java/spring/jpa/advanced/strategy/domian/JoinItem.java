package spring.jpa.advanced.strategy.domian;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

/**
 * 조인 전략
 *  - 조인 전략은 상속 관계에 있는 엔티티를 테이블로 매핑할 때 사용
 *
 * 단일 테이블 전략
 *  - @DiscriminatorColumn 기본설정
 *
 * @DiscriminatorColumn : 자식클래스 구분 컬럼 설정
 *  - 기본값은 "DTYPE"
 * @DiscriminatorValue : 자식클래스에 type name 설정 가능
 *
 * 구현 클래스 마다 테이블 전략
 *  - 구현 클래스마다 별도의 테이블을 생성
 *  - 잘 사용 X
 */
//@Entity
@Getter
@Setter
//@Inheritance(strategy = InheritanceType.JOINED) // 조인 전략
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 구현 클래스 마다 테이블 전략 | 잘 사용 X
@DiscriminatorColumn
public class JoinItem {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
}
