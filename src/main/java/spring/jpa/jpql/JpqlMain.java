package spring.jpa.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import spring.jpa.SpringJpaApplication;
import spring.jpa.jpql.domain.MemberJpql;

import java.util.List;

@Component
public class JpqlMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            MemberJpql member = new MemberJpql();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            // 반환 타입이 명확한 경우 TypedQuery
            TypedQuery<MemberJpql> query = em.createQuery(
                    "select m from MemberJpql m",
                    MemberJpql.class
            );
            // 다건 조회 ( 결과가 없을 경우 빈 리스트 반환)
            List<MemberJpql> resultList = query.getResultList();
            // 단건 조회 ( 결과가 없거나 2개 이상일 경우 예외 발생)
            MemberJpql resultOne = query.getSingleResult();
            // Spring Data JPA에서는 단건 조회 시 Optional로 감싸서 반환

            // 파라미터 바인딩
            TypedQuery<MemberJpql> query2 = em.createQuery(
                    "select m.username from MemberJpql m where m.age = :age",
                    MemberJpql.class
            );
            query2.setParameter("age", 10);
            MemberJpql memberJpql = query2.getSingleResult();

            // 메서드 체인 가능
            MemberJpql methodChain = em.createQuery("select m.username from MemberJpql m where m.age = :age", MemberJpql.class)
                    .setParameter("age", 10)
                    .getSingleResult();

            // 반환 타입이 명확하지 않을 경우 Query
            Query query3 = em.createQuery(
                    "select m.username, m.age from MemberJpql m");

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
