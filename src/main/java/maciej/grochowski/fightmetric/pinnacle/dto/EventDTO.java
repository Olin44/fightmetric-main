package maciej.grochowski.fightmetric.pinnacle.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "home",
        "away",
        "periods"
})
public class EventDTO {

    public EventDTO() {
    }

    @JsonProperty("home")
    private String fighter1;

    @JsonProperty("away")
    private String fighter2;

    @JsonProperty("periods")
    private Periods periods;

    @JsonProperty("home")
    public String getFighter1() {
        return fighter1;
    }

    @JsonProperty("home")
    public void setFighter1(String fighter1) {
        this.fighter1 = fighter1;
    }

    @JsonProperty("away")
    public String getFighter2() {
        return fighter2;
    }

    @JsonProperty("away")
    public void setFighter2(String fighter2) {
        this.fighter2 = fighter2;
    }

    @JsonProperty("periods")
    public Periods getPeriods() {
        return periods;
    }

    @JsonProperty("periods")
    public void setPeriods(Periods periods) {
        this.periods = periods;
    }
}
