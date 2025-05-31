package spring.jpa.standard.mapping.toone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import spring.jpa.SpringJpaApplication;
import spring.jpa.standard.mapping.toone.domain.ToOneMember;
import spring.jpa.standard.mapping.toone.domain.ToOneTeam;

//@Component
public class ToOneMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
//            ToOneTeam team = new ToOneTeam();
//            team.setName("Team A");
//            em.persist(team);
//
//            ToOneMember member = new ToOneMember();
//            member.setUsername("member1");
//            member.setTeamId(team.getId());
//            em.persist(member);
//
//            ToOneMember findMember = em.find(ToOneMember.class, member.getId()); // ToOneMember 조회
//            Long findTeamId = findMEmber.getTeamId(); // ToOneMember의 teamId 조회
//            ToOneTeam findTeam = em.find(ToOneTeam.class, findTeamId); // teamId로 ToOneTeam 조회

            // 1. 저장
            ToOneTeam team = new ToOneTeam();
            team.setName("Team A");
            em.persist(team);

            ToOneMember member = new ToOneMember();
            member.setUsername("member1");
            member.setTeam(team); // ToOneTeam 객체를 직접 설정
            em.persist(member);

            // 2. 조회
            ToOneMember findMember = em.find(ToOneMember.class, member.getId()); // ToOneMember 조회
            ToOneTeam findTeam = findMember.getTeam(); // ToOneMember의 team 조회
            System.out.println("Member Username: " + findMember.getUsername());
            System.out.println("Member TeamName 1: " + findTeam.getName());

            // 3. 수정
            em.find(ToOneTeam.class, findTeam.getId()).setName("Team B");
            System.out.println("Member TeamName 2: " + findTeam.getName());

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
