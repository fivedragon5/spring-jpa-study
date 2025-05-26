package spring.jpa.standard.hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import spring.jpa.SpringJpaApplication;

/**
 * 준영속 상태
 *  영속 -> 준영속
 *  영속 상태의 엔티티가 영속 상태에서 분리
 *  영속성 컨텍스트 제공하는 기능을 사용 못함
 *
 * 준영속 상태로 만드는법
 *  1. em.detach(entity) : 특정 엔티티만 준영속 상태로 변환
 *  2. em.clear() : 영속성 컨텍스트의 모든 엔티티를 분리
 *  3. em.close() : 영속성 컨텍스트를 종료
 */

@Component
public class DetachedState {
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
