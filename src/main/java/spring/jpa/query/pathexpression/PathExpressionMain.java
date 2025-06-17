package spring.jpa.query.pathexpression;

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
public class PathExpressionMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            TeamJpql teamJpql = new TeamJpql();
            em.persist(teamJpql);

            MemberJpql member1 = new MemberJpql();
            member1.setUsername("관리자 1");
            member1.setTeam(teamJpql);
            em.persist(member1);

            MemberJpql member2= new MemberJpql();
            member2.setUsername("관리자 2");
            member2.setTeam(teamJpql);
            em.persist(member2);

            em.flush();
            em.clear();

//            String query = "select m.username from MemberJpql m left join m.team t";

            // 묵시적 내부 조인 발생
//            String query = "select m.team from MemberJpql m";

            // 컬렉션 값 연관 경로
//            String query = "select t.members from TeamJpql t";

            String query = "select m.username from TeamJpql t join t.members m";
            List result = em.createQuery(query, List.class)
                    .getResultList();

            for (Object o : result) {
                System.out.println("o = " + o);
            }

//            for (MemberJpql s : result) {
//                System.out.println("s = " + s);
//            }

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
