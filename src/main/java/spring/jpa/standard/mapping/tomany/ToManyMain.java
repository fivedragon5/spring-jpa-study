package spring.jpa.standard.mapping.tomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import spring.jpa.SpringJpaApplication;
import spring.jpa.standard.mapping.tomany.domain.ToManyMember;
import spring.jpa.standard.mapping.tomany.domain.ToManyTeam;

import java.util.List;

@Component
public class ToManyMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            ToManyTeam team = new ToManyTeam();
            team.setName("Team C");
            em.persist(team);

            ToManyMember member = new ToManyMember();
            member.setUsername("member5");
            member.setTeam(team); // ToManyTeam 객체를 직접 설정
            em.persist(member);

//            team.getMembers().add(member);

//            em.flush();
//            em.clear();

            ToManyTeam findTeam = em.find(ToManyTeam.class, team.getId()); // ToManyTeam 조회
            List<ToManyMember> members = findTeam.getMembers(); // ToManyTeam의 members 조회
            System.out.println("==check START==");
            for (ToManyMember m : members) {
                System.out.println("Member Username: " + m.getUsername());
            }
            System.out.println("==check END");

//            ToManyTeam team = new ToManyTeam();
//            team.setName("Team A");
//            em.persist(team);
//
//            ToManyMember member = new ToManyMember();
//            member.setUsername("member1");
//            member.setTeam(team); // ToOneTeam 객체를 직접 설정
//            em.persist(member);

//            em.flush();
//            em.clear();
//
//            ToManyMember findMember = em.find(ToManyMember.class, member.getId()); // ToManyMember 조회
//            List<ToManyMember> members = findMember.getTeam().getMembers(); // ToManyMember의 team에서 members 조회
//
//            for (ToManyMember m : members) {
//                System.out.println("Member Username: " + m.getUsername());
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
