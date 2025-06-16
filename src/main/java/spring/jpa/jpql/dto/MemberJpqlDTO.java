package spring.jpa.jpql.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJpqlDTO {
    private String username;
    private int age;

    public MemberJpqlDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
