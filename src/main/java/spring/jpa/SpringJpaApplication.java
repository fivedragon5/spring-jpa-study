package spring.jpa;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJpaApplication {

	@Autowired
	EntityManagerFactory emf;

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaApplication.class, args);
	}
}
