package spring.jpa.standard.hellojpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *  기본 키 매핑
 *
 *  @Id
 *   - ID 직접 할당
 *
 *  @GeneratedValue
 *   - 자동 생성
 *    - IDENTITY : 데이터베이스에 위임, MYSQL
 *    - SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용, ORACLE @SequenceGenerator 필요
 *    - TABLE : 키 생성용 테이블 사용, 모든 DB 사용, @TableGenerator 필요
 *    - AUTO : 방언에 따라 자동 지정 기본값 :
 *
 */
//@Entity
@Table(name = "member3")
public class Member3 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Member3() {

    }
}
