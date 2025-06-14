package spring.jpa.valuetype.domain.embedded;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Period {
    LocalDateTime startDate;
    LocalDateTime endDate;

    public boolean isWork() {
        return startDate != null && endDate != null && !startDate.isAfter(endDate);
    }
}
