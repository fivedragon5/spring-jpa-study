package spring.jpa.standard.hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import spring.jpa.SpringJpaApplication;

/**
 * 플러시
 *  영속성 컨텍스트의 변경 내용을 DB에 반영하는 작업
 *
 *  발생
 *   - 변경 감지
 *   - 수정된 엔티티 쓰기 지연 SQL 저장소에 저장
 *   - 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송
 *
 *   영속성 컨텍스트를 플러시 하는 방법
 *    - em.flush() : 영속성 컨텍스트를 강제로 플러시
 *    - 트랜잭션 커밋 시점에 자동으로 플러시
 *    - JPQL 쿼리 실행 시점에 자동으로 플러시
 *
 *   플래시 모드 옵션
 *   em.setFlushMode(FlushModeType.COMMIT);
 *    - AUTO : 커밋이나 쿼리를 실행할 때 플러시 (기본값)
 *    - COMMIT : 커밋 시점에만 플러
 *
 *  주의할점
 *   - 영속성 컨텍스트를 비우지 않음
 *   - 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화
 *   - 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 하면 됨
 *
 */

@Component
public class flush {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member(200L, "FIVE FLUSH");
            em.persist(member);
            // 강제 호출
            em.flush();
            // console에 출력되기 전 쿼리 실행
            System.out.println(" =============== ");
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
