package maciej.grochowski.fightmetric.pinnacle.dto;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "money_line"
})
public class Number {

    public Number() {
    }

    @JsonProperty("money_line")
    private MoneyLine moneyLine;

    @JsonProperty("money_line")
    public MoneyLine getMoneyLine() {
        return moneyLine;
    }

    @JsonProperty("money_line")
    public void setMoneyLine(MoneyLine moneyLine) {
        this.moneyLine = moneyLine;
    }

}
