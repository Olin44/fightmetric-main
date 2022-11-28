package maciej.grochowski.fightmetric.ufcstats.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class FinalDTO {

    private String name1;
    private BigDecimal odds1;
    private List<Advantage> advantages1;
    private String name2;
    private BigDecimal odds2;
    private List<Advantage> advantages2;

    public FinalDTO(String name1, List<Advantage> advantages1, String name2, List<Advantage> advantages2) {
        this.name1 = name1;
        this.advantages1 = advantages1;
        this.name2 = name2;
        this.advantages2 = advantages2;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public BigDecimal getOdds1() {
        return odds1;
    }

    public List<Advantage> getAdvantages1() {
        return advantages1;
    }

    public void setAdvantages1(List<Advantage> advantages1) {
        this.advantages1 = advantages1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public BigDecimal getOdds2() {
        return odds2;
    }

    public List<Advantage> getAdvantages2() {
        return advantages2;
    }

    public void setAdvantages2(List<Advantage> advantages2) {
        this.advantages2 = advantages2;
    }

    public void setOdds1(BigDecimal odds1) {
        this.odds1 = odds1;
    }

    public void setOdds2(BigDecimal odds2) {
        this.odds2 = odds2;
    }

    @Override
    public String toString() {
        return "FinalDTO{" +
                "name1='" + name1 + '\'' +
                ", advantages1=" + advantages1 +
                ", odds1=" + odds1 +
                ", name2='" + name2 + '\'' +
                ", advantages2=" + advantages2 +
                ", odds2=" + odds2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinalDTO finalDTO = (FinalDTO) o;
        return name1.equals(finalDTO.name1) && name2.equals(finalDTO.name2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name1, name2);
    }
}
