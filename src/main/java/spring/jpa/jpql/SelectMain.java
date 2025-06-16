package spring.jpa.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import spring.jpa.SpringJpaApplication;
import spring.jpa.jpql.domain.AddressJpql;
import spring.jpa.jpql.domain.MemberJpql;
import spring.jpa.jpql.domain.OrderJpql;

import java.util.List;

@Component
public class SelectMain {
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

            em.flush();
            em.clear();

            // 영속성 컨텍스트로 관리 됨
            List<MemberJpql> result = em.createQuery("select m from MemberJpql m", MemberJpql.class)
                            .getResultList();

            MemberJpql findMember = result.get(0);
            findMember.setAge(99);

            // address 부분만
            em.createQuery("select o.address from OrderJpql o", AddressJpql.class)
                    .getResultList();

            em.flush();
            em.clear();

            System.out.println("============");

            // address 부분만
            em.createQuery("select o from OrderJpql o", OrderJpql.class)
                    .getResultList();

            // DTO로 뽑기
            List<MemberJpqlDTO> list = em.createQuery(
                    "select new spring.jpa.jpql.MemberJpqlDTO(m.username, m.age) from MemberJpql m"
                            , MemberJpqlDTO.class)
                    .getResultList();

            for (MemberJpqlDTO memberJpqlDTO : list) {
                System.out.println("memberJpqlDTO = " + memberJpqlDTO.getUsername() + ", " + memberJpqlDTO.getAge());
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
