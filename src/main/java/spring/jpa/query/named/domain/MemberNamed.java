package spring.jpa.query.named.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NamedQuery(
        name = "MemberNamed.findByUsername",
        query = "select m from MemberNamed m where m.username = :username"
)
public class MemberNamed {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private TeamNamed team;

    public void changeTeam(TeamNamed team) {
        this.team = team;
        team.getMembers().add(this);
    }

    @Override
    public String toString() {
        return "MemberJpql{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", team=" + team +
                '}';
    }
}
