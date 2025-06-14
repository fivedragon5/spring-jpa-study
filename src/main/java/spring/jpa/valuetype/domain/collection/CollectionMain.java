package spring.jpa.valuetype.domain.collection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import spring.jpa.SpringJpaApplication;
import spring.jpa.valuetype.domain.AddressEntity;
import spring.jpa.valuetype.domain.MemberCollectionType;
import spring.jpa.valuetype.domain.embedded.Address;

import java.util.List;
import java.util.Set;

//@Component
public class CollectionMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            MemberCollectionType member = new MemberCollectionType();
            member.setName("John Doe");
            member.setHomeAddress(new Address("New York", "5th Avenue", "10001"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");



            member.getAddressHistory().add(new AddressEntity("old1", "Gangnam", "12345"));
            member.getAddressHistory().add(new AddressEntity("old2", "Gangnam", "12345"));

            // member를 저장할때 같이 저장된다(음식, 주소)
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("===========");
            // 멤버만 조회됨
            // -> 다른 값들은 조회 안됨 (지연로딩)
            MemberCollectionType findMember = em.find(MemberCollectionType.class, member.getId());

            List<AddressEntity> addressHistory = findMember.getAddressHistory();
            for (AddressEntity address : addressHistory) {
//                System.out.println("address = " + address.getCity() + ", " + address.getStreet() + ", " + address.getZipcode());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String food : favoriteFoods) {
                System.out.println("food = " + food);
            }

            // 업데이트할땐 새로운 객체를 생성해서 넣기
            findMember.setHomeAddress(new Address("new city", "new street", "12345"));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // address의 equals, hashCode가 있어야 정확한 값 삭제 가능
            // 조건이 memberId인 모든 값 삭제 후 필요한값 전부 insert (위험)
            findMember.getAddressHistory().remove(new AddressEntity("old1", "Gangnam", "12345"));;
            findMember.getAddressHistory().add(new AddressEntity("new city77", "Gangnam", "12345"));;
            // 값 타입 컬렉션에 변경사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고
            // 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장

            em.persist(findMember);

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
