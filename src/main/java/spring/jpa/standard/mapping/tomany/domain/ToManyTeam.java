package spring.jpa.standard.mapping.tomany.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

//@Entity
public class ToManyTeam {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "team")
    private List<ToManyMember> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ToManyMember> getMembers() {
        return members;
    }
}
