package spring.jpa.bulk;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import spring.jpa.SpringJpaApplication;
import spring.jpa.jpql.domain.MemberJpql;
import spring.jpa.jpql.domain.TeamJpql;

@Component
public class BulkOperationMain {
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
            member1.setAge(10);
            em.persist(member1);

            MemberJpql member2 = new MemberJpql();
            member2.setUsername("회원2");
            member2.setTeam(teamJpql1);
            member2.setAge(20);
            em.persist(member2);

            MemberJpql member3 = new MemberJpql();
            member3.setUsername("회원3");
            member3.setTeam(teamJpql2);
            member3.setAge(30);
            em.persist(member3);

//            MemberJpql member4 = new MemberJpql();
//            member4.setUsername("회원4");
//            em.persist(member4);

//            em.flush();
//            em.clear();

            // FLUSH 자동 호출
            // 모든 회원의 나이를 20으로 변경하는 bulk update
            int resultCount = em.createQuery("update MemberJpql m set m.age = 20")
                    .executeUpdate();
            // 영속성 컨텍스트 초기화 필요
            em.clear();

            // 변경된 회원의 나이를 다시 조회
            MemberJpql findMember1 = em.find(MemberJpql.class, member1.getId());
            MemberJpql findMember2 = em.find(MemberJpql.class, member2.getId());
            MemberJpql findMember3 = em.find(MemberJpql.class, member3.getId());

            System.out.println("Updated Members Count: " + resultCount);

            System.out.println("member1.getAge() = " + findMember1.getAge());
            System.out.println("member2.getAge() = " + findMember2.getAge());
            System.out.println("member3.getAge() = " + findMember3.getAge());

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
