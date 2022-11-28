package maciej.grochowski.fightmetric.pinnacle.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class EventDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fighter1;
    private String fighter2;
    private BigDecimal odds1;
    private BigDecimal odds2;

    public EventDB() {
    }

    public EventDB(String fighter1, String fighter2, BigDecimal odds1, BigDecimal odds2) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
        this.odds1 = odds1;
        this.odds2 = odds2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFighter1() {
        return fighter1;
    }

    public void setFighter1(String fighter1) {
        this.fighter1 = fighter1;
    }

    public String getFighter2() {
        return fighter2;
    }

    public void setFighter2(String fighter2) {
        this.fighter2 = fighter2;
    }

    public BigDecimal getOdds1() {
        return odds1;
    }

    public void setOdds1(BigDecimal odds1) {
        this.odds1 = odds1;
    }

    public BigDecimal getOdds2() {
        return odds2;
    }

    public void setOdds2(BigDecimal odds2) {
        this.odds2 = odds2;
    }

    @Override
    public String toString() {
        return fighter1 + " " + odds1 + " - " + odds2 + " " + fighter2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDB eventDB = (EventDB) o;
        return fighter1.equals(eventDB.fighter1) && fighter2.equals(eventDB.fighter2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fighter1, fighter2);
    }
}
