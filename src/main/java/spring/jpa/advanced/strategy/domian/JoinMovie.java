package spring.jpa.advanced.strategy.domian;

import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter
@Setter
public class JoinMovie extends JoinItem {
    private String director;
    private String actor;
}
