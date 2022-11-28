package maciej.grochowski.fightmetric.pinnacle.client;

import maciej.grochowski.fightmetric.pinnacle.dto.UFCMarket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "fightsClient",
        url = "https://pinnacle-odds.p.rapidapi.com/kit/v1",
        configuration = FlightsClientConfiguration.class
)
public interface FightsClient {

    @GetMapping("/markets?sport_id=8&league_ids=1624&is_have_odds=true")
    UFCMarket getUFCMarkets();
}
