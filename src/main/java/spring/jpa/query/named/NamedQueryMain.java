package spring.jpa.query.named;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import spring.jpa.SpringJpaApplication;
import spring.jpa.query.named.domain.MemberNamed;
import spring.jpa.query.named.domain.TeamNamed;

import java.util.List;

@Component
public class NamedQueryMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            TeamNamed teamNamed1 = new TeamNamed();
            teamNamed1.setName("팀 A");
            em.persist(teamNamed1);

            TeamNamed teamNamed2 = new TeamNamed();
            teamNamed2.setName("팀 B");
            em.persist(teamNamed2);

            TeamNamed teamNamed3 = new TeamNamed();
            teamNamed3.setName("팀 C");
            em.persist(teamNamed3);


            MemberNamed member1 = new MemberNamed();
            member1.setUsername("회원1");
            member1.setTeam(teamNamed1);
            em.persist(member1);

            MemberNamed member2 = new MemberNamed();
            member2.setUsername("회원2");
            member2.setTeam(teamNamed1);
            em.persist(member2);

            MemberNamed member3 = new MemberNamed();
            member3.setUsername("회원3");
            member3.setTeam(teamNamed1);
            em.persist(member3);

            em.flush();
            em.clear();

            List<MemberNamed> result = em.createNamedQuery("MemberNamed.findByUsername", MemberNamed.class)
                    .setParameter("username", "회원2")
                    .getResultList();

            for (MemberNamed m : result) {
                System.out.println("Member: " + m);
            }

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
