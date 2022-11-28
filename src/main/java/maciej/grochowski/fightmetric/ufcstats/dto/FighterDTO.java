package maciej.grochowski.fightmetric.ufcstats.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FighterDTO {

    private String name;
    private BigDecimal height;
    private BigDecimal reach;
    private BigDecimal strikesLanded;
    private BigDecimal strikingAccuracy;
    private BigDecimal strikesAbsorbed;
    private BigDecimal strikesDefence;
    private BigDecimal takedownsAverage;
    private BigDecimal takedownsAccuracy;
    private BigDecimal takedownsDefence;
    private LocalDate dob;
    private List<Advantage> advantages = new ArrayList<>();

    public FighterDTO() {
    }

    public FighterDTO(String name, BigDecimal height, BigDecimal reach, BigDecimal strikesLanded, BigDecimal strikingAccuracy,
                      BigDecimal strikesAbsorbed, BigDecimal strikesDefence, BigDecimal takedownsAverage, BigDecimal takedownsAccuracy,
                      BigDecimal takedownsDefence, LocalDate dob) {
        this.name = name;
        this.height = height;
        this.reach = reach;
        this.strikesLanded = strikesLanded;
        this.strikingAccuracy = strikingAccuracy;
        this.strikesAbsorbed = strikesAbsorbed;
        this.strikesDefence = strikesDefence;
        this.takedownsAverage = takedownsAverage;
        this.takedownsAccuracy = takedownsAccuracy;
        this.takedownsDefence = takedownsDefence;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getReach() {
        return reach;
    }

    public void setReach(BigDecimal reach) {
        this.reach = reach;
    }

    public BigDecimal getStrikesLanded() {
        return strikesLanded;
    }

    public void setStrikesLanded(BigDecimal strikesLanded) {
        this.strikesLanded = strikesLanded;
    }

    public BigDecimal getStrikingAccuracy() {
        return strikingAccuracy;
    }

    public void setStrikingAccuracy(BigDecimal strikingAccuracy) {
        this.strikingAccuracy = strikingAccuracy;
    }

    public BigDecimal getStrikesAbsorbed() {
        return strikesAbsorbed;
    }

    public void setStrikesAbsorbed(BigDecimal strikesAbsorbed) {
        this.strikesAbsorbed = strikesAbsorbed;
    }

    public BigDecimal getStrikesDefence() {
        return strikesDefence;
    }

    public void setStrikesDefence(BigDecimal strikesDefence) {
        this.strikesDefence = strikesDefence;
    }

    public BigDecimal getTakedownsAverage() {
        return takedownsAverage;
    }

    public void setTakedownsAverage(BigDecimal takedownsAverage) {
        this.takedownsAverage = takedownsAverage;
    }

    public BigDecimal getTakedownsAccuracy() {
        return takedownsAccuracy;
    }

    public void setTakedownsAccuracy(BigDecimal takedownsAccuracy) {
        this.takedownsAccuracy = takedownsAccuracy;
    }

    public BigDecimal getTakedownsDefence() {
        return takedownsDefence;
    }

    public void setTakedownsDefence(BigDecimal takedownsDefence) {
        this.takedownsDefence = takedownsDefence;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public List<Advantage> getAdvantages() {
        return advantages;
    }

    public void setAdvantages(List<Advantage> advantages) {
        this.advantages = advantages;
    }

    @Override
    public String toString() {
        return "FighterDTO{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", reach=" + reach +
                ", strikesLanded=" + strikesLanded +
                ", strikingAccuracy=" + strikingAccuracy +
                ", strikesAbsorbed=" + strikesAbsorbed +
                ", strikesDefence=" + strikesDefence +
                ", takedownsAverage=" + takedownsAverage +
                ", takedownsAccuracy=" + takedownsAccuracy +
                ", takedownsDefence=" + takedownsDefence +
                ", dob=" + dob +
                '}';
    }
}
