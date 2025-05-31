package spring.jpa.standard.hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import spring.jpa.SpringJpaApplication;
import spring.jpa.standard.jpashop.domain.Member;

/**
 * 데이터 베이스 스키마 자동 생성
 *  1. create : 기존 테이블 생성 후 다시 생성
 *  2. create-drop : create와 동일하나 종료 시점에 테이블 DROP
 *  3. update : 변경분만 반영
 *  4. validation : 엔티티와 테이블이 정상 매핑되었는지만 확인
 *  5. none : 사용 X
 *
 * DDL 생성 기능
 *  - @Column(unique = true, length = 10)
 *   - 제약 조건 추가 가능
 *  -> DDL 생성 기능은 DDL 생성에만 영향을 주고 런타임에는 영향을 주지 않는다.
 */

@Component
public class SchemaAutoCreate {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 준영속 상태로 만드는 법
            Member member1 = em.find(Member.class, 1L);
            member1.setName("ABCD");

            // 영속 상태에서 제(준영속)
            em.detach(member1);

            Member member2 = em.find(Member.class, 150L);
            em.clear();
            // em.close();

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
