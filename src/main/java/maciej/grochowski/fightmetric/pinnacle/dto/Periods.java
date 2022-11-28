package maciej.grochowski.fightmetric.pinnacle.dto;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "num_0"
})
public class Periods {

    public Periods() {
    }

    @JsonProperty("num_0")
    private Number number;

    @JsonProperty("num_0")
    public Number getNumber() {
        return number;
    }

    @JsonProperty("num_0")
    public void setNumber(Number number) {
        this.number = number;
    }
}
