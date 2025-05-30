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
 * 필드와 컬럼 매핑
 *
 */

@Component
public class FieldAndColumnMapping {
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
