package spring.jpa.standard.hellojpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import spring.jpa.standard.hellojpa.type.RoleType;

import java.util.Date;

/**
 * 어노테이션 정리
 *   - @Column      : 컬럼 매핑
 *      - name  : 필드와 매핑할 테이블의 컬럼 이름
 *      - insertable, updatable : 등록 변경 가능 여부
 *      - nullable(DDL) : null 값의 허용 여부
 *      - unique(DDL) : @Table uniqueConstrains 와 같지만 한 컬럼에 간단히 유니크 제약 조건
 *      - columnDefinition : 데이터 베이스 컬럼 정보를 직접 줄 수 있다.
 *      - length(DDL) : 문자 길이 제약 조건 (String 타입에만 사용)
 *      - precision, scale(DDL) : BigDecimal,BigInteger 타입에만 사용
 *       - precision : 소수점을 포함한 전체 자릿수
 *       - scale : 소수점의 자릿수
 *   - @Temporal    : 날짜 타입 매핑
 *   - @Enumerated  : enum 타입 매핑
 *   - @Lob         : BLOB, CLOB 매핑
 *      - Type이 String 이면 CLOB 아닐 경우 BLOB
 *   - @Transient   : 특정 필드 컬럼 매핑 X
 */
@Entity
public class Member2 {

    @Id
    private Long id;

    @Column(name = "name")
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // DATE, TIME, TIMESTAMP
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob // 일반적인 데이터베이스에서 저장하는 길이인 255개 이상의 문자를 저장하고 싶을 때 지정한다.
    private String description;

    public Member2() {

    }
}
