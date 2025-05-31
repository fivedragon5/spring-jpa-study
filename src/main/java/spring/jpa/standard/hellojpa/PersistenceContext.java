package spring.jpa.standard.hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import spring.jpa.SpringJpaApplication;

/**
 * 영속성 컨텍스트
 *  준영속 : 회원 엔티티를 영속석 컨텍스트 에서 분리
 *  삭제 : 객체를 삭제한 상태
 * 트랜젝션을 커밋하는 순간 쿼리가 실행
 *
 * 영속성 컨텍스트의 이점
 *  - 1차 캐시
 *  - 동일성 보장
 *  - 트랜잭션을 지원하는 쓰기 지연
 *  - 변경 감지
 *  - 지연 로딩
 *
 *
 */

@Component
public class PersistenceContext {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("FIVE GOGO");
//
//            // 영속 상태로 변경
//            System.out.println("=== BEFORE");
//            em.persist(member);
//            System.out.println("== AFTER");
//
//            // DB에서 조회하지 않고 1차 캐쉬에서 조회
//            Member findedMember = em.find(Member.class, 101L);
//            System.out.println("findedMember Id = " + findedMember.getId());
//            System.out.println("findedMember Name = " + findedMember.getName());

            // DB에서 조회
//            Member findedMember1 = em.find(Member.class, 101L);
//            // 1차 캐쉬에서 조회
//            Member findedMember2 = em.find(Member.class, 101L);
            // 쿼리가 1번만 나감

            // 영속엔티티의 동일성 보장
//            System.out.println("result =  " + (findedMember1 == findedMember2));
//
//            // 엔티티 등록 트랜젝션을 지원하는 쓰기 지연
//            Member member1 = new Member(150L, "FIVE MEMBER 150");
//            Member member2 = new Member(160L, "FIVE MEMBER 160");
//            em.persist(member1);
//            em.persist(member2);
//
//            // 엔티티 수정 변경 감지
//            Member findedMemberA = em.find(Member.class, 150);
//            // commit 시점에 읽어온 시점, 스냅샷 시점을 비교하여 다를 경우 update 쿼리 실행
//            findedMemberA.setName("FIVE MEMBER 150 TEST");



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
