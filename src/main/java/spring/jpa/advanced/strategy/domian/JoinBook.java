package spring.jpa.advanced.strategy.domian;

import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter
@Setter
public class JoinBook extends JoinItem {
    private String author;
    private String isbn;
}
