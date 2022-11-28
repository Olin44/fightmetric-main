package maciej.grochowski.fightmetric.pinnacle.dto;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "home",
        "draw",
        "away"
})
public class MoneyLine {

    public MoneyLine() {
    }

    @JsonProperty("home")
    private Double home;

    @JsonProperty("away")
    private Double away;

    @JsonProperty("home")
    public Double getHome() {
        return home;
    }

    @JsonProperty("home")
    public void setHome(Double home) {
        this.home = home;
    }

    @JsonProperty("away")
    public Double getAway() {
        return away;
    }

    @JsonProperty("away")
    public void setAway(Double away) {
        this.away = away;
    }
}
