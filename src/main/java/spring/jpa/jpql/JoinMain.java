package spring.jpa.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import spring.jpa.SpringJpaApplication;
import spring.jpa.jpql.domain.MemberJpql;
import spring.jpa.jpql.domain.TeamJpql;

import java.util.List;

@Component
public class JoinMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            TeamJpql team = new TeamJpql();
            team.setName("teamA");
            em.persist(team);

            MemberJpql member = new MemberJpql();
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(team); // 연관관계 설정

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m from MemberJpql m left join m.team t";
            List<MemberJpql> result = em.createQuery(query, MemberJpql.class)
                    .getResultList();

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
