package spring.jpa.standard.hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import spring.jpa.SpringJpaApplication;
import spring.jpa.standard.hellojpa.entity.Member;

/**
 * 1. EntityManagerFactory 애플리케이션 전체에서 하나만 생성해서 공유
 * 2. 엔티티매니저는 쓰레드 간의 공유X (사용하고 삭제)
 * 3. JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
 *
 * JPQL
 *  1. JPA는 SQL을 추상화한 JPQL 이라는 객체 지향 쿼리 언어 제공
 *  2. SQL문법과 유사
 *  3. JPQL은 엔티티 객체를 대상으로 쿼리
 *  4. SQL은 데이터베이스 테이을 대상으로 쿼린
 */
@Component
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
