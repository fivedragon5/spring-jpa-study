package spring.jpa.query.fetchjoin;

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
public class FetchJoin2Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            TeamJpql teamJpql1 = new TeamJpql();
            teamJpql1.setName("팀 A");
            em.persist(teamJpql1);

            TeamJpql teamJpql2 = new TeamJpql();
            teamJpql2.setName("팀 B");
            em.persist(teamJpql2);

            TeamJpql teamJpql3 = new TeamJpql();
            teamJpql3.setName("팀 C");
            em.persist(teamJpql3);


            MemberJpql member1 = new MemberJpql();
            member1.setUsername("회원1");
            member1.setTeam(teamJpql1);
            em.persist(member1);

            MemberJpql member2 = new MemberJpql();
            member2.setUsername("회원2");
            member2.setTeam(teamJpql1);
            em.persist(member2);

            MemberJpql member3 = new MemberJpql();
            member3.setUsername("회원3");
            member3.setTeam(teamJpql2);
            em.persist(member3);

//            MemberJpql member4 = new MemberJpql();
//            member4.setUsername("회원4");
//            em.persist(member4);

            em.flush();
            em.clear();

            String query = "select t From TeamJpql t join fetch t.members m";
            List<TeamJpql> result = em.createQuery(query, TeamJpql.class)
                    .getResultList();
            for (TeamJpql m : result) {
                System.out.println("Team Name: " + m.getName());
                for (MemberJpql member : m.getMembers()) {
                    System.out.println("  Member Username: " + member.getUsername());
                }
            }

            em.flush();
            em.clear();

            System.out.println("========================== 77 ");

            // 컬렉션 fetch join 의 페이징
            // 모든 데이터를 조회한 뒤 메모리에서 페이징 처리 -> 사용 X
            //String collectionFetchJoinQuery = "select t from TeamJpql t join fetch t.members";

            // 쿼리 수정 - 1
            // String collectionFetchJoinQuery = "select m from MemberJpql m join fetch m.team t";

            // 쿼리 수정 - 2 LAZY Loading 성능 이슈
            // BatchSize 설정을 통해 성능 개선 가능
            String collectionFetchJoinQuery = "select t from TeamJpql t";

            List<TeamJpql> collectionFetchJoinResult = em.createQuery(collectionFetchJoinQuery, TeamJpql.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("collectionFetchJoinResult = " + collectionFetchJoinResult.size());

            for (TeamJpql t : collectionFetchJoinResult) {
                System.out.println("Team Name: " + t.getName());
                for (MemberJpql member : t.getMembers()) {
                    System.out.println("  Member Username: " + member.getUsername());
                }
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
