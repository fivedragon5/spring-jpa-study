package spring.jpa.standard.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import spring.jpa.SpringJpaApplication;
import spring.jpa.standard.jpashop.domain.Order;
import spring.jpa.standard.jpashop.domain.OrderItem;

//@Component
public class JpaMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Order order = new Order();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);

            em.persist(order);

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
