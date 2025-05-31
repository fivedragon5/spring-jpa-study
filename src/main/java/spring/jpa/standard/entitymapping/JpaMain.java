package spring.jpa.standard.entitymapping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import spring.jpa.SpringJpaApplication;
import spring.jpa.standard.jpashop.domain.Member;

/**
 * @Entity
 *  1. Entity가 붙은 클래스는 JPA과 관리
 *  2. JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
 *  주의
 *   - 기본 생성자 필수 (파라미터가 없는 public 또는 protected 생성자)
 *   - final 클래스, enum, interface, inner 클래스 사용 X
 *   - 저장할 필드에 final 사용 X
 *
 * @Table
 *  - 엔티티와 매핑할 테이블을 지정
 */
//@Component
public class JpaMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 1. 멤버 저장
            Member member = new Member();
            member.setId(2L);
            member.setName("FIVE");
            em.persist(member);

            // 2. 멤버 조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember Id = " + findMember.getId());
            System.out.println("findMember Name = " + findMember.getName());

            // 3. 멤버 수정
            findMember.setName("FIVE TEST");

            tx.commit();
            System.out.println("Entity saved successfully using EntityManagerFactory directly!");
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
