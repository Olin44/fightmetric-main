package maciej.grochowski.fightmetric.pinnacle.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "events"
})
public class UFCMarket {

    public UFCMarket() {
    }

    @JsonProperty("events")
    private List<EventDTO> eventDTOS = null;

    @JsonProperty("events")
    public List<EventDTO> getEvents() {
        return eventDTOS;
    }

    @JsonProperty("events")
    public void setEvents(List<EventDTO> eventDTOS) {
        this.eventDTOS = eventDTOS;
    }

}
