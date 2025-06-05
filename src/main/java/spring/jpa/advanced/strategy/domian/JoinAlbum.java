package spring.jpa.advanced.strategy.domian;

import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter
@Setter
public class JoinAlbum extends JoinItem {
    private String artist;
}
