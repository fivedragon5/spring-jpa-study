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
public class FetchJoinMain {
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

            // N + 1 문제
            String query = "select m From MemberJpql m ";
            List<MemberJpql> result = em.createQuery(query, MemberJpql.class)
                    .getResultList();
            for (MemberJpql m : result) {
                // System.out.println("m = " + m.getUsername() + ", teamName = " + m.getTeam().getName());
                // 회원1, 팀A(SQL)
                // 회원2, 팀A(1차캐시)
                // 회원3, 팀B(SQL)

                // 회원 100명 -> N + 1
                // 최악의 경우 : 회원 조회 쿼리 1번 + 팀 조회 쿼리 N번
            }

            System.out.println("====================");

            // Fetch Join
            String fetchJoinQuery = "select m From MemberJpql m join fetch m.team";
            List<MemberJpql> fetchJoinResult = em.createQuery(fetchJoinQuery, MemberJpql.class)
                    .getResultList();
            for (MemberJpql m : fetchJoinResult) {
                // team은 proxy가 아니라 실제 엔티티가 조회된다.
                System.out.println("m = " + m.getUsername() + ", teamName = " + m.getTeam().getName());
            }

            em.flush();
            em.clear();

            System.out.println("====================");

            // 컬렉션 fetch join
            String collectionFetchJoinQuery = "select t from TeamJpql t join fetch t.members";

            List<TeamJpql> collectionFetchJoinResult = em.createQuery(collectionFetchJoinQuery, TeamJpql.class)
                    .getResultList();
            for (TeamJpql t : collectionFetchJoinResult) {
                System.out.println("t = " + t.getName() + ", members = " + t.getMembers().size());
                // hibernate 6버전 이상부터는 중복 제거가 기본값으로 설정되어 있다.
                for (MemberJpql m : t.getMembers()) {
                    System.out.println("member = " + m.getUsername());
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
