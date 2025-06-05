package spring.jpa.advanced.strategy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import spring.jpa.SpringJpaApplication;
import spring.jpa.advanced.strategy.domian.JoinMovie;

//@Component
public class JoinMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            JoinMovie movie = new JoinMovie();
            movie.setDirector("Christopher Nolan");
            movie.setActor("Leonardo DiCaprio");
            movie.setName("Inception");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            JoinMovie findMovie = em.find(JoinMovie.class, movie.getId()); // 영속성 컨텍스트에 저장된 엔티티를 조회
            System.out.println("영속성 컨텍스트에 저장된 엔티티: " + findMovie);

            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
